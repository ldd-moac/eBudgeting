var DetailModalView = Backbone.View.extend({
	/**
	 * @memberOf DetailModalView
	 */
	initialize: function(options) {
		
	},
	
	el: '#detailModal',
	
	detailModalTemplate : Handlebars.compile($('#detailModalTemplate').html()),
	detailModalMainFooterTemplate: Handlebars.compile($('#detailModalMainFooterTemplate ').html()),
	detailAllocationRecordFooterTemplate: Handlebars.compile($('#detailAllocationRecordFooterTemplate ').html()),
	
	detailAllocationRecordStrategyTemplate: Handlebars.compile($('#detailAllocationRecordStrategyTemplate ').html()),
	detailAllocationRecordStrategyFooterTemplate: Handlebars.compile($('#detailAllocationRecordStrategyFooterTemplate ').html()),
	
	detailAllocationBasicFooterTemplate: Handlebars.compile($('#detailAllocationBasicFooterTemplate').html()),
	detailAllocationBasicTemplate : Handlebars.compile($('#detailAllocationBasicTemplate').html()),
	
	detailViewTableTemplate: Handlebars.compile($('#detailViewTableTemplate').html()),
	detailAllocationRecordTemplate: Handlebars.compile($('#detailAllocationRecordTemplate').html()),
	
	detailAllocationRecordBasicTemplate: Handlebars.compile($('#detailAllocationRecordBasicTemplate').html()),
	
	detailModalBudgetHeaderTemplate: Handlebars.compile($('#detailModalBudgetHeaderTemplate').html()),
	detailModalBudgetTemplate: Handlebars.compile($('#detailModalBudgetTemplate').html()),
	reservedBudgetInputTemplate: Handlebars.compile($('#reservedBudgetInputTemplate').html()),
	
	strategiesTemplate: Handlebars.compile($('#strategiesTemplate').html()),
	editProposalFormTemplate : Handlebars.compile($('#editProposalFormTemplate').html()),
	editProposalNoStretegyFormTemplate : Handlebars.compile($('#editProposalNoStretegyFormTemplate').html()),
	
	events: {
		"click .detailAllocation" : "detailAllocation",
		"click #cancelBtn" : "cancelBtn",
		"click #backBtn" : "backBtn",
		"click #saveBtn" : "saveBtn",
		"change .txtForm" : "changeTxtForm"
	},
	changeTxtForm: function(e) {
		var topBudgetTypeId = this.$el.find('form').attr('data-id');
		var currAlloc = this.topAllocation['id'+topBudgetTypeId];
		
		var sum = 0;
		this.$el.find('.txtForm').each(function(index, txt) {
			
			sum += parseInt($(txt).val());
		});
		
		currAlloc.amountAllocated = sum;
		
		currAlloc.amountToBeAllocated =  currAlloc.allocR9.amountAllocated - currAlloc.amountAllocated;
		
		this.$el.find('#summaryAllocation').html("จัดสรรให้เจ้าของงาน : "+ addCommas(currAlloc.allocR9.amountAllocated)+" บาท  จัดสรรไปแล้ว "+addCommas(currAlloc.amountAllocated)+" บาท คงเหลือการจัดสรร "+ addCommas(currAlloc.amountToBeAllocated)+" บาท")
		
	},
	saveBtn: function(e) {
		var values = [];
		// we'll try to save
		var input = this.$el.find('.txtForm');
		_.each(input, function(el){
			var id = $(el).attr('data-id');
			var orgAllocRecord = OrganizationAllocationRecord.findOrCreate({id: id});
			var value = parseInt($(el).val());
			
			if(orgAllocRecord!=null) {
				orgAllocRecord.set('amountAllocated', value);
				values.push(orgAllocRecord.toJSON());
			}
		});
		
		
		console.log(values);
		
		// now save to the database?
		
		$.ajax({
			url: appUrl('/OrganizationAllocationRecord/LotsUpdate'),
			type: 'PUT',
    		data: JSON.stringify(values),
    		dataType: 'json',
    		contentType: 'application/json'})
    	.done(_.bind(function(msg) {
    			alert('บันทึกเรียบร้อย');
    			var store=Ext.getStore('treeObjectiveStore');
    			var node = store.getNodeById(this.currentObjective.get('id'));
    			var changeAmount = 0;
    			
    			for(var i=0; i<values.length; i++) {
    			
    				var orgAllocRec = values[i]; 	
    				
    				var allocNode = _.findWhere(node.data.filterOrgAllocRecords, {id: parseInt(orgAllocRec.id)});
    				changeAmount += orgAllocRec.amountAllocated - allocNode.amountAllocated;
    				allocNode.amountAllocated = orgAllocRec.amountAllocated;
    				
    			}
    			
    			node.data.sumOrgAllocRecords  += changeAmount;
    			node.data.amountAllocationLeft = node.data.sumAllocationR9 - node.data.sumOrgAllocRecords;
    			
    			node.commit();
    			
    			
    		},this));		
	},
	setParentView: function(view) {
		this.parentView = view;
	},
	cancelBtn : function(e) {
		this.$el.modal('hide');
		// this.parentView.reloadTable();
		
	},

	backBtn : function(e) {
		this.render();
		// this.parentView.reloadTable();
		
	},

	renderWithObjective : function(objective) {
		this.currentObjective = objective;
		this.render();
	},
	render: function() {
		
		this.$el.find('.modal-header span').html(this.currentObjective.get('name'));
		this.$el.find('.modal-footer').html(this.detailModalMainFooterTemplate());
		
		var json = this.currentObjective.toJSON();
		
		
		var html = this.detailModalTemplate(json);
		this.$el.find('.modal-body').html(html);
		
		
		this.$el.modal({show: true, backdrop: 'static', keyboard: false});
		// now render table
		
		var sumTopBudgetType = {};
		var allocRec = {};
		var ownerProposals = this.currentObjective.get('proposals')
		
		_.forEach(json.filterOrgAllocRecords, _.bind(function(orgAllocRecord) {
			var budgetType = BudgetType.findOrCreate({id: orgAllocRecord.budgetType.id});
			
			if(sumTopBudgetType['id'+budgetType.get('id')] == null) {
				sumTopBudgetType['id'+budgetType.get('id')] = {};
				allocRec = sumTopBudgetType['id'+budgetType.get('id')];
				allocRec.amountAllocated=0;
				allocRec.budgetType = budgetType.toJSON();

				var allocR9 = this.currentObjective.get('allocationRecordsR9').findWhere({budgetType: budgetType});
				if(allocR9!=null) {
					allocRec.allocR9 = allocR9.toJSON();
				}
				
				allocRec.orgAllocRecs = new Array();
				
				
			} else {
				allocRec = sumTopBudgetType['id'+budgetType.get('id')];
			}
			
			allocRec.amountAllocated += orgAllocRecord.amountAllocated;
			
			var p = ownerProposals.filter(function(pp) { 
				if(pp.get('budgetType').get('topParentName') == budgetType.get('name') && 
						pp.get('owner').get('id') == orgAllocRecord.owner.id) 
					return true; 
				else 
					return false;
				});
			
			var sumBudgetProposal = 0;
			
			_.forEach(p, _.bind(function(op) {
				
				sumBudgetProposal += op.get('amountRequest');
				
			},this));
			
			orgAllocRecord.sumBudgetProposal = sumBudgetProposal;
			
			allocRec.orgAllocRecs.push(orgAllocRecord);
			
		}, this) );
		
//		//prepare the allocation
//		_.forEach(json.sumBudgetTypeProposals, _.bind(function(proposal) {
//			var budgetType = BudgetType.findOrCreate({id:proposal.budgetType.id});
//			// search the allocationR1
//						
//			if(sumTopBudgetType['id'+budgetType.get('parentIds')[1]] == null) {
//				sumTopBudgetType['id'+budgetType.get('parentIds')[1]] = {};
//				allocRec = sumTopBudgetType['id'+budgetType.get('parentIds')[1]];
//				allocRec.amountAllocatedR1 = 0;
//				allocRec.amountAllocatedR2 = 0;
//				allocRec.amountAllocatedR3 = 0;
//				allocRec.amountReserved = 0;
//				allocRec.amountToBeAllocated = 0;
//				allocRec.amountAllocated = 0;
//				allocRec.topParentName = budgetType.get('topParentName');
//				allocRec.topBudgetTypeId = proposal.budgetType.parentIds[1];
//				
//				var topBudgetType=BudgetType.findOrCreate({id:allocRec.topBudgetTypeId});
//				
//				allocRec.proposals = new Array();
//				
//				// then set allocR9 and amountReserved
//				
//				var allocR9 = this.currentObjective.get('allocationRecordsR9').findWhere({budgetType: topBudgetType});
//				if(allocR9!=null) {
//					allocRec.allocR9 = allocR9.toJSON();
//				}
//				
//				var reservedBudget = this.currentObjective.get('reservedBudgets').findWhere({budgetType: topBudgetType});
//				if(reservedBudget!=null) {
//					allocRec.reservedBudget = reservedBudget.toJSON();
//				}
//								
//			} else {
//				allocRec = sumTopBudgetType['id'+budgetType.get('parentIds')[1]];
//			}
//			
//			var r3 = this.currentObjective.get('allocationRecordsR3').findWhere({budgetType:budgetType});
//
//			allocRec.amountAllocatedR3 +=  r3.get('amountAllocated');
//		
//			var o =  ownerProposals.where({budgetType: budgetType});
//			proposal.ownerProposals = [];
//			for(var i=0; i< o.length; i++) {
//				proposal.ownerProposals.push(o[i].toJSON());
//			}
//		
//			allocRec.proposals.push(proposal);
//			
//		},this));
//		
		this.topAllocation = sumTopBudgetType;
		
		_.each(this.topAllocation, function(alloc) {
			alloc.amountToBeAllocated = alloc.allocR9.amountAllocated - alloc.amountAllocated;
		});
		
		html = this.detailViewTableTemplate(sumTopBudgetType);
		this.$el.find('#detailModalDiv').html(html);
		
		this.$el.find('.modal-footer').html('<a href="#" class="btn" id="cancelBtn">กลับหน้าหลัก</a>');
		
		
		return this;
	},
	detailAllocation: function(e) {
		this.currentTopBudgetTypeId = $(e.target).attr('data-budgetTypeId');
		this.renderBudgetTypeDetail();
		
	},
	
	renderBudgetTypeDetail: function() {
		var topBudget = this.topAllocation['id'+this.currentTopBudgetTypeId];
		
		html = this.detailModalBudgetHeaderTemplate(topBudget);
		this.$el.find('.modal-body').html(html);
		
		html = this.detailModalBudgetTemplate(topBudget);
		this.$el.find('.modal-body').append(html);
		
		this.$el.find('.modal-footer').html('<a href="#" class="btn" id="saveBtn">บันทึก</a><a href="#" class="btn" id="backBtn">กลับหน้าเดิม</a>');
		
	}
	

	
});

var ModalView = Backbone.View.extend({
	/**
     *  @memberOf ModalView
     */
	initialize: function() {
		
	},
	
	el: "#modal",
	
	modalTemplate: Handlebars.compile($('#modalTemplate').html()),
	modalFormTemplate: Handlebars.compile($('#inputModalTemplate').html()),
	
	events: {
		"click #cancelBtn" : "cancelModal",
		"click #saveBtn" : "saveModal"
	},
	
	cancelModal: function(e) {
		  window.location.reload();
	},
	
	saveModal: function(e) {
		var amountAllocated = $('#amountAllocated').val();
		var allocationRecordId = $('#amountAllocated').attr('data-id');
		
		var record = AllocationRecord.findOrCreate(allocationRecordId);
		
		var newAmount = parseInt(amountAllocated);
		
		record.set('amountAllocated', newAmount);
		
		if(record == null) {
			// Post a new allocation Record
			record = new AllocationRecord();
			record.set('amountAllocated', amountAllocated);
			record.set('budgetType', budgetType);
			record.set('forObjective', this.objectvie);
		} else {
		
			// now try to save this..
			$.ajax({
				type: 'PUT',
				url: appUrl('/AllocationRecord/'+record.get('id')),
				contentType: 'application/json;charset=utf-8',
				dataType: "json",
				data: JSON.stringify(record.toJSON()),
				success: function() {
					window.location.reload();
				}
			});
		}
		
	},
	
	
	render: function() {
		if(this.objective != null) {
			
			this.$el.find('.modal-header span').html(this.objective.get('name'));
			
			var json =this.budgetProposalCollection.toJSON();
			json.budgetType = this.budgetType.toJSON();
			
			
			var html = this.modalTemplate(json);
			this.$el.find('.modal-body').html(html);
			
			html = this.modalFormTemplate(this.allocationRecord.toJSON());
			this.$el.find('.modal-body').append(html);
						
		}
		
		
		this.$el.modal({show: true, backdrop: 'static', keyboard: false});
		return this;
	},
	
	renderWith: function(currentObjective, currentAllocationRecord, currentBudgetType, budgetProposalCollection) {
		this.objective = currentObjective;
		this.allocationRecord = currentAllocationRecord;
		this.budgetType = currentBudgetType;
		this.budgetProposalCollection = budgetProposalCollection;
		this.render();
	}
});

var TargetValueModalView=Backbone.View.extend({
	/**
	 * @memberOf TargetValueModalView
	 */
	initialize: function() {
		
	},
	
	el: "#targetValueModal",
	
	events : {
		"click #saveBtn" : "saveTargetValue",
		"click #cancelBtn" : "cancelTargetValue",
	},
	
	targetValueModalTpl : Handlebars.compile($("#targetValueModalTemplate").html()),
	render: function() {
		
		
		this.$el.find('.modal-header span').html(this.objective.get('name'));
		var json = {};
		json.target = this.targetValue.toJSON();
		json.values = [];
		
		this.objective.get('targetValues').each(function(target) {
			if(target.get('target').get('unit').get('id') == json.target.target.unit.id) {
				json.values.push(target.toJSON());
			}
			
		});
		
		var html = this.targetValueModalTpl(json);
		this.$el.find('.modal-body').html(html);

		
		
		this.$el.modal({
			show : true,
			backdrop : 'static',
			keyboard : false
		});
		return this;
	},
	cancelTargetValue: function() {
		this.$el.modal('hide');
	},
	saveTargetValue: function() {
		var values = [];
		// we'll try to save
		var input = this.$el.find('.txtForm');
		_.each(input, function(el){
			var id = $(el).attr('data-id');
			var target = TargetValue.findOrCreate({id: id});
			var value = parseInt($(el).val());
			
			if(target!=null) {
				target.set('allocatedValue', value);
				values.push(target.toJSON());
			}
		});
		
		
		console.log(values);
		
		// now save to the database?
		
		$.ajax({
			url: appUrl('/TargetValue/LotsUpdate'),
			type: 'PUT',
    		data: JSON.stringify(values),
    		dataType: 'json',
    		contentType: 'application/json'})
    	.done(_.bind(function(msg) {
    			alert('บันทึกเรียบร้อย');
    		},this));
	},
	
	renderWith: function(objective, targetId, valueId) {
		this.objective = objective;
		this.objectiveTarget=ObjectiveTarget.findOrCreate(targetId);
		this.targetValue=TargetValueAllocationRecord.findOrCreate(valueId);
		if(this.targetValue == null) {
			this.targetValue = new TargetValue();
			this.targetValue.set('forObjective', objective);
			this.targetValue.set('target', this.objectiveTarget);
		}
		this.render();
	}

});

var MainSelectionView = Backbone.View.extend({
	/**
	 * @memberOf MainSelectionView
	 */
	mainSelectionTemplate : Handlebars.compile($("#mainSelectionTemplate").html()),
	selectionTemplate : Handlebars.compile($("#selectionTemplate").html()),
	type102DisabledSelectionTemplate : Handlebars.compile($("#type102DisabledSelection").html()),
	type103DisabledSelectionTemplate : Handlebars.compile($("#type103DisabledSelection").html()),
	
	
	initialize: function() {
		
		this.type102Collection = new ObjectiveCollection();
		this.type103Collection = new ObjectiveCollection();
		
		_.bindAll(this, 'renderInitialWith');
		_.bindAll(this, 'renderType102');
		_.bindAll(this, 'renderType103');
		this.type102Collection.bind('reset', this.renderType102);
		this.type103Collection.bind('reset', this.renderType103);
	},
	events: {
		"change select#type101Slt" : "type101SltChange",
		"change select#type102Slt" : "type102SltChange",
		"change select#type103Slt" : "type103SltChange"
	},
	type101SltChange : function(e) {
		var type101Id = $(e.target).val();
		if(type101Id != 0) {
			this.type102Collection.fetch({
				url: appUrl('/Objective/' + type101Id + '/children'),
				success: _.bind(function() {
					this.type102Collection.trigger('reset');
				}, this)
			});
		}
		
		mainCtrView.emptyTbl();
		
	},
	type102SltChange : function(e) {
		var type102Id = $(e.target).val();
		if(type102Id != 0) {
			this.type103Collection.fetch({
				url: appUrl('/Objective/' + type102Id + '/children'),
				success: _.bind(function() {
					this.type103Collection.trigger('reset');
				}, this)
			});
		}
		
		this.$el.find('#type103Div').empty();
		this.$el.find('#type103Div').html(this.type103DisabledSelectionTemplate());
		
		mainCtrView.emptyTbl();
	},
	
	type103SltChange : function(e) {
		var type103Id = $(e.target).val();
		if(type103Id != 0) {
			var obj = Objective.findOrCreate(type103Id);
			mainCtrView.renderMainTblWithParent(obj);
			
		} else {
			mainCtrView.emptyTbl();
		}
	
	},
	
	renderType102: function(e) {
		var json = this.type102Collection.toJSON();
		json.type =  {};
		json.type.name = "ผลผลิต/โครงการ";
		json.type.id = "102";
		var html = this.selectionTemplate(json);
		
		// now render 
		this.$el.find('#type102Div').empty();
		this.$el.find('#type102Div').html(html);
		
		this.$el.find('#type103Div').empty();
		this.$el.find('#type103Div').html(this.type103DisabledSelectionTemplate());
		
		
	},
	
	renderType103: function(e) {
		var json = this.type103Collection.toJSON();
		json.type =  {};
		json.type.name = "กิจกรรมหลัก";
		json.type.id = "103";
		var html = this.selectionTemplate(json);
		
		// now render 
		this.$el.find('#type103Div').empty();
		this.$el.find('#type103Div').html(html);
		
		
	},
	
	render: function() {
		
		if(this.rootChildrenObjectiveCollection != null) {
			var json = this.rootChildrenObjectiveCollection.toJSON();
			
			var html = this.mainSelectionTemplate(json);
			this.$el.html(html);
		}
	}, 
	renderInitialWith: function(objective) {
		
		e1=this;
		
		this.rootObjective = objective;
		
		// now get this rootObjective Children
		this.rootChildrenObjectiveCollection = new ObjectiveCollection();
		
		this.rootChildrenObjectiveCollection.fetch({
			url: appUrl('/Objective/' + this.rootObjective.get('id') + '/children'),
			success : _.bind(function() {
				
				this.render();
			},this)
		});
		
	}
	
});



var MainCtrView = Backbone.View.extend({
	/**
	 * @memberOf MainCtrView
	 */
	initialize: function(){
	    //this.collection.bind('reset', this.render, this);
	    _.bindAll(this, 'detailModal');
	},
	
	el: "#mainCtr",
	mainCtrTemplate : Handlebars.compile($("#mainCtrTemplate").html()),
	loadingTemplate : Handlebars.compile($("#loadingTemplate").html()),
	
	detailModalView: new DetailModalView(),
	modalView : new ModalView(),
	targetValueModalView : new TargetValueModalView(),
	
	
	events:  {
		"click input[type=checkbox].bullet" : "toggle",
		"click .detail" : "detailModal",
		"click .targetValueModal" : "targetValueModal",
	},
	
	targetValueModal: function(e) {
		var currentObjectiveId = $(e.target).attr('objective-id');
		var targetId = $(e.target).attr('target-id');
		var valueId = $(e.target).attr('value-id');
		var currentObjective = Objective.findOrCreate({id:currentObjectiveId});
		
		this.targetValueModalView.renderWith(currentObjective, targetId, valueId);
	},
	
	detailModal: function(e) {
		
		var currentObjectiveId = $(e.target).attr('data-objectiveId');
		
		this.currentObjective = Objective.find({id:currentObjectiveId});
		this.detailModalView.setParentView(this);
		this.detailModalView.renderWithObjective(this.currentObjective);
		
		return false;
		
	},
	render : function() {
		this.$el.html(this.mainCtrTemplate());
		this.mainSelectionView = new MainSelectionView({el: "#mainCtr #mainSelection"});
		
					
		this.rootObjective = new Objective(); 
		this.rootObjective.fetch({
			url: appUrl('/Objective/ROOT/'+fiscalYear),
			success : _.bind(function() {
				this.mainSelectionView.renderInitialWith(this.rootObjective);
			},this)
		});
	},
	
	emptyTbl: function(e) {
		this.$el.find('#mainTbl').empty();
	},
	renderMainTblWithParent: function(parentObjective){
		this.currentParentObjective = parentObjective;
		this.renderMainTbl();
	},
	reloadTable: function() {
		//this.treeStore.reload();
	},
	renderMainTbl: function() {
		
		this.collection = new ObjectiveCollection();
		//this.rootCollection = new ObjectiveCollection();
		
		this.collection.url = appUrl("/ObjectiveWithBudgetProposalAndAllocation/"+ fiscalYear + "/" + this.currentParentObjective.get('id') +"/flatDescendants");
		
		this.$el.find('#mainTbl').html(this.loadingTemplate());
		
		this.collection.fetch({
			success: _.bind(function() {
				var json = {};
				json.objectives = this.collection.toJSON();
				
				this.$el.find('#mainTbl').empty();
				this.treeStore = Ext.create('Ext.data.TreeStore', {
			        model: 'data.Model.Objective',
			        storeId: 'treeObjectiveStore',
			        proxy: {
			            type: 'memory',
			            data: json.objectives,
			            render: {
			            	type: 'json'
			            }
			        },
			        folderSort: false
			    });
				
				if(this.tree !=null) {
					this.tree.destroy();
				}
				
				this.tree = Ext.create('Ext.tree.Panel', {
					id: 'treeGrid',
			        title: 'การของบประมาณ',
			        width: 920,
			        height: 300,
			        renderTo: Ext.getElementById('mainTbl'),
			        collapsible: false,
			        rootVisible: false,
			        store: this.treeStore,
			        columnLines: true, 
			        rowLines: true,
			        multiSelect: true,
			        frame: true,
			        columns: [{
			             //this is so we know which column will show the tree
			        	xtype: 'treecolumn',
			            text: 'กิจกรรมรอง',
			            sortable: true,
			            dataIndex: 'codeAndName',
			        	width: 300,
			            locked: true,
			            renderer: function(value, metaData, record, rowIdx, colIdx, store) {
			                metaData.tdAttr = 'data-qtip="' + value + '"';
			                if(record.data.children == null || record.data.children.length == 0) {
			                	return "<a href='#' data-objectiveId=" + record.data.id +  " class='detail'>"+ value + "</a>";	
			                }
			                return value;
			            }
			        }, {
			        	text: 'เป้าหมาย',
			        	width: 90,
			        	sortable: false,
			        	dataIndex: 'targetValueAllocationRecordsRound',
			        	align: 'center',
			        	renderer: function(value, metaData, record, rowIdx, colIdx, store) {
			        		var html="";
			        		for(var i=0; i<value.length; i++ ) {
			        			if(i>0) {
			        				html += "<br/>";
			        			}
			        			var unit = TargetUnit.findOrCreate(value[i].target.unit);
			        			html += "<a href='#' class='targetValueModal' objective-id='"+value[i].forObjective+"' value-id='"+value[i].id+"' target-id='"+value[i].target.id+"'>"+addCommas(value[i].amountAllocated)+" " + unit.get('name') + "</a>"; 
			        		}
			        		return html;
			        	}
			        }, {
			        	text: 'จัดสรรให้เจ้าของงาน',
			        	width: 100,
			        	sortable : false,
			        	dataIndex: 'sumAllocationR9',
			        	align: 'right',
			        	renderer: function(value) {
			        		return addCommas(value);
			        	}
			        		
			        }, {
			        	text: 'จัดสรรเพิ่มเติม',
			        	width: 100,
			        	sortable : false,
			        	dataIndex: 'sumAllocationAfterR9',
			        	align: 'right',
			        	renderer: function(value) {
			        		return addCommas(value);
			        	}
			        		
			        }, {
			        	text: 'รวมเงินที่ได้รับการจัดสรร',
			        	width: 100,
			        	sortable : false,
			        	dataIndex: 'sumAllocation',
			        	align: 'right',
			        	renderer: function(value) {
			        		return addCommas(value);
			        	}
			        		
			        }, {
			        	text: 'จัดสรรให้หน่วยงาน',
			        	width: 100,
			        	sortable : false,
			        	dataIndex: 'sumOrgAllocRecords',
			        	align: 'right',
			        	renderer: function(value) {
			        		return addCommas(value);
			        	}
			        		
			        }, {
			        	text: 'เหลือจัดสรร',
			        	width: 100,
			        	sortable : false,
			        	dataIndex: 'amountAllocationLeft',
			        	align: 'right',
			        	renderer: function(value) {
			        		return addCommas(value);
			        	}
			        		
			        }]
				});
				
			}, this)	
		});
	}
});
	