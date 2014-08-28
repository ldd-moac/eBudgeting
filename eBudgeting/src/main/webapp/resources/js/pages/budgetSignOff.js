var MainCtrView = Backbone.View.extend({
	/**
     *  @memberOf MainCtrView
     */
	initialize : function() {
		
	},

	el : "#mainCtr",
	events : {
		"click button.topRow" : "buttonClick"
	},
	
	mainTblTemplate : Handlebars.compile($("#mainTblTemplate").html()),
	signOffLogTbodyTemplate : Handlebars.compile($("#signOffLogTbodyTemplate").html()),
	
	render : function() {
		
		if(this.budgetSignOff != null) {
			var json = this.budgetSignOff.toJSON();
			if(round == 0) {
				json.headline = json.owner.name;
			}  else {
				json.headline = "งบประมาณที่ขอตั้งรวม";
			}
			this.$el.find('#signOffTbl').html(this.mainTblTemplate(json));
		}
	},
	
	renderWith : function(budgetSignOff) {
		this.budgetSignOff = budgetSignOff;
		this.render();
		
		signOfflogs.fetch({
			url: appUrl('/BudgetSignOffLog/'+fiscalYear+'/Round/'+round),
			success: _.bind(function() {
				this.renderLogs();
			},this)
		})
	},
	
	renderLogs : function() {
		var json = signOfflogs.toJSON();
		this.$el.find('#signOffLog').html(this.signOffLogTbodyTemplate(json));
	},
	
	
	
	buttonClick: function(e) {
		var buttonId = $(e.target).prop('id');
		if(buttonId == 'lock1'  || buttonId == 'lock2') {
			if(sumObjectiveProposal != sumProposal) {
				alert("ยอดรวมเงินกิจกรรมกับเงินรายการไม่เทากัน กรุณากระทบยอดก่อน SignOff");
				return false;
			}
		}
		
		
		$.get(appUrl('/BudgetSignOff/'+fiscalYear +'/R'+round+'/updateCommand/' + buttonId),
				_.bind(function(response) {
					
					var log = new BudgetSignOffLog(response);
					
					this.budgetSignOff.set(buttonId+'Person', log.get('person'));
					this.budgetSignOff.set(buttonId+'TimeStamp', log.get('timestamp'));
					
					if(buttonId == 'lock1') {
						this.budgetSignOff.set('unLock1Person', null);
						this.budgetSignOff.set('unLock1TimeStamp', null);
						
					} else if(buttonId == 'lock2') {
						this.budgetSignOff.set('unLock2Person', null);
						this.budgetSignOff.set('unLock2TimeStamp', null);
						
					} else if(buttonId == 'unLock1') {
						this.budgetSignOff.set('lock1Person', null);
						this.budgetSignOff.set('lock1TimeStamp', null);
						
					} else if(buttonId == 'unLock2') {
						this.budgetSignOff.set('lock2Person', null);
						this.budgetSignOff.set('lock2TimeStamp', null);
					}
					this.render();
					
					signOfflogs.add(log, {at:0});
					this.renderLogs();
		},this));
	}
	
});