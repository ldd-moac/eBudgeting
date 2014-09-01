var MainTblView = Backbone.View.extend({
	/**
	 * @memberOf MainTblView
	 */
	initialize: function(options){
		this.collection = new ObjectiveCollection();
		this.collection.bind('reset', this.render, this);

		
		if(options.rootObjectiveId != null) {
			var childrenObjectives = new ObjectiveCollection();
			childrenObjectives.url = appUrl('/Objective/' + options.rootObjectiveId + '/childrenWithAllocations/R2');
			childrenObjectives.fetch({
				success: _.bind(function() {
					childrenObjectives.each(_.bind(function(objective){
						this.collection.push(objective);
					},this));
					this.collection.trigger('reset');
				},this)
			});
			
		} 
	    
	},

	el: "#mainCtr",
	events: {
		"click .drillDown" : "loadChildren",
		"click .rollUp" : "unloadChildren",
		"click #processBtn" : "processCalculation"
	},
	
	mainTblTemplate: Handlebars.compile($("#mainTblTemplate").html()),
	mainTblTbodyTemplate: Handlebars.compile($("#mainTblTbodyTemplate").html()),
	processingTemplate :Handlebars.compile($("#processingTemplate").html()),
	
	processCalculation: function(e) {
		var answer = confirm("คุณต้องการประมวลผลข้อมูลคำขอตั้งงบประมาณจากรายการไปสู่กิจกรรม");
		if(answer ==  true) {
			this.$el.html(this.processingTemplate());
			$.ajax({
				url: appUrl('/BudgetProposal/'+ fiscalYear +'/copyFromAllocationToObjectiveAllocation/R2'),
				success: function() {
					// alert
					alert('ดำเนินการเรียบร้อย');
					// reload 
					window.location.reload();
				}
			});
		}
	},
	unloadChildren: function(e) {
		var parentId = $(e.target).parents('tr').attr('data-id');
		var parentObjective = Objective.findOrCreate(parentId);
		
		this.$el.find('#caret-' + parentId).removeClass('icon-chevron-down');
		this.$el.find('#link-' + parentId).removeClass('rollUp');
		var currTR = $(e.target).parents('tr');
		var currTRmargin = parseInt(currTR.children().first().css('padding-left'), 10); 
		var nextTR = $(e.target).parents('tr').next();
		var nextTRmargin = parseInt(nextTR.children().first().css('padding-left'), 10);
		console.log("currTR: " + currTR.attr('data-id') + " : nextTr.size() = " + nextTR.size());
		console.log("currTR style:" + currTR.children().first().attr('style'));
		console.log("nextTR style:" + nextTR.children().first().attr('style'));
		
		while(nextTR.size() > 0 && 
				currTRmargin < nextTRmargin) {
			
			
			var nextNextTR = nextTR.next();
			nextTR.remove();
			nextTR = nextNextTR;
			nextTRmargin = parseInt(nextTR.children().first().css('padding-left'), 10);
		}
		
		this.$el.find('#link-' + parentId).addClass('drillDown');
		this.$el.find('#caret-' + parentId).addClass('icon-chevron-right');
		
	},
	loadChildren: function(e) {
		var parentId = $(e.target).parents('tr').attr('data-id');
		var parentObjective = Objective.findOrCreate(parentId);
		
		// spinning load
		this.$el.find('#caret-' + parentId).removeClass('icon-chevron-right');
		this.$el.find('#link-' + parentId).removeClass('drillDown');
		
		this.$el.find('#caret-' + parentId).addClass('icon-spin icon-refresh');
		this.$el.find('#link-' + parentId).addClass('rollUp');
		
		if(parentObjective.get('children') == null || parentObjective.get('children').size() == 0 ) {
			var childrenObjectives = new ObjectiveCollection();
			childrenObjectives.url = appUrl('/Objective/' + parentId + '/childrenWithAllocations/R2');
			childrenObjectives.fetch({
				success: _.bind(function() {
					parentObjective.set('children', childrenObjectives);
					childrenObjectives.each(_.bind(function(child){
						child.set('parent', parentObjective);
					},this));
					
					var json = childrenObjectives.toJSON();
					_.each(json, function(objective) {
						objective.paddingLeft = (objective.parentLevel-1) * 20;
						objective.sumProposalAmountRequest = 0;
						objective.sumObjectiveProposalAmountRequest = 0;
						
						_.each(objective.allocationRecordsR2, function(record) {
							objective.sumProposalAmountRequest += record.amountAllocated;
						});
					
						_.each(objective.objectiveAllocationRecordsR2, function(record) {
							objective.sumObjectiveProposalAmountRequest += record.amountAllocated;
						});
					});
					var tbodyHtml = this.mainTblTbodyTemplate(json);
					this.$el.find('tr[data-id='+parentId+']').after(tbodyHtml);
					
					this.$el.find('#caret-' + parentId).removeClass('icon-spin icon-refresh');
					this.$el.find('#caret-' + parentId).addClass('icon-chevron-down');
					
					
				},this)
			});
		} else {
			var childrenObjectives = parentObjective.get('children');
			var json = childrenObjectives.toJSON();
			_.each(json, function(objective) {
				objective.paddingLeft = (objective.parentLevel-1) * 20;
				objective.sumProposalAmountRequest = 0;
				objective.sumObjectiveProposalAmountRequest = 0;
				
				_.each(objective.allocationRecordsR2, function(record) {
					objective.sumProposalAmountRequest += record.amountAllocated;
				});
			
				_.each(objective.objectiveAllocationRecordsR2, function(record) {
					objective.sumObjectiveProposalAmountRequest += record.amountAllocated;
				});
			});
			var tbodyHtml = this.mainTblTbodyTemplate(json);
			this.$el.find('tr[data-id='+parentId+']').after(tbodyHtml);
			this.$el.find('#caret-' + parentId).removeClass('icon-spin icon-refresh');
			this.$el.find('#caret-' + parentId).addClass('icon-chevron-down');
		}
		return false;
	},
	
	render: function() {
		var html = this.mainTblTemplate();
		this.$el.html(html);

		var json = this.collection.toJSON();
		_.each(json, function(objective) {
			objective.paddingLeft = (objective.parentLevel-1) * 20;
			objective.sumProposalAmountRequest = 0;
			objective.sumObjectiveProposalAmountRequest = 0;
			
			_.each(objective.allocationRecordsR2, function(record) {
				objective.sumProposalAmountRequest += record.amountAllocated;
			});
		
			_.each(objective.objectiveAllocationRecordsR2, function(record) {
				objective.sumObjectiveProposalAmountRequest += record.amountAllocated;
			});
			
		});
		var tbodyHtml = this.mainTblTbodyTemplate(json);
		this.$el.find('tbody').html(tbodyHtml);
		
		return this;
	}
});