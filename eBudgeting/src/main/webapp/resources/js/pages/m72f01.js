var MainCtrView = Backbone.View.extend({
	/**
	 * @memberOf MainCtrView
	 */
	initialize: function(){
		
	},
	
	el: "#mainCtr",
	mainTblTemplate : Handlebars.compile($("#mainTblTemplate").html()),
	mainTblTbodyTpl : Handlebars.compile($("#mainTblTbodyTemplate").html()),
	
	
	events:  {
		"click #processBtn" : "onClickProcessBtn"
	},
	
	onClickProcessBtn: function() {
		
		$.ajax({
			type: 'GET',
			url: appUrl('/OrganizationAllocationRecord/'+ fiscalYear +'/newRound'),
			success: _.bind(function() {
				this.render();
			},this)
		})
		
		
	},
	
	
	render : function() {
		this.collection = new OrganizationAllocationRoundCollection();
		this.collection.fetch({
			url: appUrl('/OrganizationAllocationRound/'+ fiscalYear),
			success: _.bind(function() {
				var json = this.collection.toJSON();
				this.$el.html(this.mainTblTemplate());
				this.$el.find('tbody').html(this.mainTblTbodyTpl(json));
				
			},this)
		});
	}
	
});
	