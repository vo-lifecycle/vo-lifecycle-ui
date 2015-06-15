/**
 * 
 * @author anthony attia <anthony.attia1@gmail.com>
 *
 */
$(function() {

	var url = "http://localhost:8080/mas-cmd/GenerateJsonLifeCycleServlet?action=showList" ;
	$.ajax(url, {
		type: 'GET', // it's easier to read GET request parameters
		dataType: 'json',
		async: true,
		success: function (data) {
			if(data != null){
				console.debug(data.length);
				data.list.forEach(function(status,index){
					$("#lifeCycle").append($('<option>', { 
						value: index,
						text : status.description 
					}));
				})

			}else{
				console.log("data is null");
			}

		},
		error: function(xhr, ajaxOptions, thrownError){
			console.error(xhr.status, thrownError);
		}
	})


	$("#submitForm").submit(function(event) {
		//$("paper").empty();

		var selectValue  = $('#lifeCycle').val();
		$("#paper").html('');	
		console.debug("test");

		var url = "http://localhost:8080/mas-cmd/GenerateJsonLifeCycleServlet?idManagerLifeCycle=" + selectValue ;
		$.ajax(url, {
			type: 'GET', // it's easier to read GET request parameters
			dataType: 'json',
			async: true,
			success: function (data) {
				if(data != null){

					createState(data.state);
					createTransition(data.state);

				}else{
					console.log("data is null");
				}

			},
			error: function(xhr, ajaxOptions, thrownError){
				console.error(xhr.status, thrownError);
			}
		})


		event.preventDefault();


		var graph = new joint.dia.Graph;
		var paper = new joint.dia.Paper({ el: $('#paper'), width: 1500, height: 2000, gridSize: 10, model: graph });
		var erd = joint.shapes.erd;

		var uml = joint.shapes.uml;

		var idCollapse = 0;


		joint.shapes.html = {};
		joint.shapes.html.Element = joint.shapes.basic.Rect.extend({
			markup: '<a><g class="rotatable"><g class="scalable"><rect/></g><text/></g></a>',
			defaults: joint.util.deepSupplement({
				type: 'html.Element',
				attrs: {
					rect: { stroke: 'none', 'fill-opacity': 0 }
				}
			}, joint.shapes.basic.Rect.prototype.defaults)
		});

		//Create a custom view for that element that displays an HTML div above it.
		//-------------------------------------------------------------------------

		joint.shapes.html.ElementView = joint.dia.ElementView.extend({

			template: '<div class="html-element-transition" data-toggle="tooltip" data-placement="bottom"></div>',
			initialize: function() {
				_.bindAll(this, 'updateBox');
				joint.dia.ElementView.prototype.initialize.apply(this, arguments);

				this.$box = $(_.template(this.template)());
				this.model.on('change', this.updateBox, this);
				// Remove the box when the model gets removed from the graph.
				this.model.on('remove', this.removeBox, this);

				this.updateBox();
			},
			render: function() {
				joint.dia.ElementView.prototype.render.apply(this, arguments);
				this.paper.$el.prepend(this.$box);
				this.updateBox();
				return this;
			},
			updateBox: function() {
				// Set the position and dimension of the box so that it covers the JointJS element.
				var bbox = this.model.getBBox();
				// Example of updating the HTML with a data stored in the cell model.
				this.$box.find('label').text(this.model.get('label'));
				this.$box.find('span').text(this.model.get('select'));
				this.$box.html(this.model.get('content'));
				this.$box.css({ width: bbox.width, height: bbox.height, left: bbox.x, top: bbox.y, transform: 'rotate(' + (this.model.get('angle') || 0) + 'deg)' });
			},
			removeBox: function(evt) {
				this.$box.remove();
			}
		});





		function createTemplateContentElementTransition(label, listAction,id){
			//arrayChecker = ["check 1", "check 2"];
			var html = '<button class="delete btn btn-success" type="button" data-toggle="collapse" data-target="#collapse'+id+'" aria-expanded="false"        aria-,'       
			+'controls="collapseExample">!</button>'
			+'<a href="#" class="btn btn-info btn-lg"><label>'+ label + '</label>'

			+'</a>'
			+'<div id= "collapse'+id+'" class="collapse blue" aria-expanded="false" style="background-color: white; width:180px;">'
			if(listAction != null){
				listAction.forEach(function(action , id){         
					html += "<p>" + action.id  + "</p>"
				})
			}
			+'<text></text>'
			+'<span></span><br/>'
			+'</div>';
			//console.log(arrayChecker);
			var elementTransition = createElementTransition(html,label);
			return elementTransition;

		}

		function createElementTransition (contentHtml,label){
			var element = new joint.shapes.html.Element({ 
				position: { x: Math.random() * (500)  + 1, y: Math.random() * (500)  + 1 }, 
				label: label, 
				size: { width: 170, height: 28 }, 
				"width" : 170,
				"height" : 28,
				content:  contentHtml

			});
			return element;
		}


		function createElement(id){
			var elm = new joint.shapes.erd.Entity({ 
				id: id,
				position: { x: Math.random() * (500)  + 1, y: Math.random() * (500)  + 1 }, 
				attrs: { text: { text: id }}

			});
			graph.addCell(elm);
			return elm;
		};

		function createLinkById(sourceState, targetState) {

			var myLink = new erd.Line({ 
				source: { id: sourceState }, 
				target: { id: targetState },

				smooth: true,
				attrs: {

					'.marker-target': { d: 'M 10 0 L 0 5 L 10 10 z' }
				}
			});


			graph.addCell(myLink);
			return myLink;
		}

		function createState(states){
			states.forEach(function(s,ide){       
				createElement(s.id);
			});
		}

		function createTransition(states){
			states.forEach(function(state,ide){
				if(state.transitionMap != null){
					$.each(state.transitionMap,function(item,trans){
						var transition = createTemplateContentElementTransition(trans.descriptionT, trans.actions, state.id + '-' +trans.id + idCollapse++);
						graph.addCells([transition]);

						createLinkById(state.id,transition.id);
						trans.targetStates.forEach(function(element,index){
							console.log(element);
							createLinkById(transition.id, element);
							
						})
//						

					});
				}

			});
		}
	});
});
