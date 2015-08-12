/**
 * 
 * @author anthony attia <anthony.attia1@gmail.com>
 *
 */
$(function() {

	//Get Json format lifecycle datas from an other servlet  
	var url = getContextPath() + "/GenerateJsonLifeCycleServlet?action=showList";

	//joint js variable

	var graph;
	var uml;
	var erd;
	var paper;

	var idCollapse = 0;

	var y = 0;

	var positionJ;
	var nbTransition = 0;
	var lengthT;


	$.ajax(url, {
		type: 'GET', // it's easier to read GET request parameters
		dataType: 'json',
		async: true,
		success: function(data) {
			if (data != null) {
				//console.debug(data.length);
				data.list.forEach(function(status, index) {
					$("#lifeCycle").append($('<option>', {
						value: index,
						text: status.description
					}));
				})

				createGraph();
				//showGraphEvent();

			} else {
				console.debug("data is null");
			}

		},
		error: function(xhr, ajaxOptions, thrownError) {
			console.error(xhr.status, thrownError);
		}
	})

	/**
	 * Call creategraph() corresponding to the lifeCycle choosed
	 *
	 */
	$("#lifeCycle").change(function(event) {

		$('#paper').load( paper);
		createGraph();
	});

	/**
	 * Set a new graph each time this function is called 
	 */
	function createGraph(){

		graph = new joint.dia.Graph;

		var selectValue = $('#lifeCycle').val();
		$("#paper").html('');
		console.debug("test");

		var url = getContextPath() + "/GenerateJsonLifeCycleServlet?idManagerLifeCycle=" + selectValue;
		$.ajax(url, {
			type: 'GET', // it's easier to read GET request parameters
			dataType: 'json',
			async: true,
			success: function(data) {
				if (data != null) {


					getNbTransitionJson(data.state);
					createState(data.state);
					createTransition(data.state);

					event();

				} else {
					console.debug("data is null");
				}

			},
			error: function(xhr, ajaxOptions, thrownError) {
				console.error(xhr.status, thrownError);
			}
		})

		paper = new joint.dia.Paper({
			el: $('#paper'),
			width: 2500,
			height: 10000,
			gridSize: 10,
			model: graph
		});
		erd = joint.shapes.erd;

		uml = joint.shapes.uml;

		joint.shapes.html = {};
		joint.shapes.html.Element = joint.shapes.basic.Rect.extend({
			markup: '<a><g class="rotatable"><g class="scalable"><rect/></g><text/></g></a>',
			defaults: joint.util.deepSupplement({
				type: 'html.Element',
				attrs: {
					rect: {
						stroke: 'none',
						'fill-opacity': 0
					}
				}
			}, joint.shapes.basic.Rect.prototype.defaults)
		});

		/**
		 * Create a custom view for that element that displays an HTML div above it.
		 */


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
				this.$box.css({
					width: bbox.width,
					height: bbox.height,
					left: bbox.x,
					top: bbox.y,
					transform: 'rotate(' + (this.model.get('angle') || 0) + 'deg)'
				});
			},
			removeBox: function(evt) {
				this.$box.remove();
			}
		});


	}
	/**
	 * 
	 * create a new transition element template
	 * 
	 */
	function createTemplateContentElementTransition(label, listAction, id, x,y, lengthT) {
		//arrayChecker = ["check 1", "check 2"];
		var html = '<button class="delete btn btn-success" type="button" data-toggle="collapse" data-target="#collapse' + id + '" aria-expanded="false"        aria-,' + 'controls="collapseExample">!</button>' + '<a href="#" class="btn btn-info btn-lg" style="min-width:272px;"><label>' + label + '</label>'

		+'</a>' + '<div id= "collapse' + id + '" class="collapse blue" aria-expanded="false" style="background-color: white; width:300px;">'
		if (listAction != null) {
			listAction.forEach(function(action, index) {
				if(action.actions == null) 
					html += "&nbsp <a >" + action.description + "</a><br /><br />"
					else{
						html += "&nbsp <a  href='#'>" + action.description + "</a><br /><br />"
						html += "<ul>"
							action.actions.forEach(function(sa,id){
								html += "<li><p  href='#'>" + sa.description + "</p></li><br />"
							})
							html += "</ul>"
					}
			})
		} + '<text></text>' + '<span></span><br/>' + '</div>';
		//console.debug(arrayChecker);
		var elementTransition = createElementTransition(html, label, x, y, lengthT);
		return elementTransition;

	}

	/**
	 *   
	 *  create transition element with an html template
	 *
	 */
	function createElementTransition(contentHtml, label, x,y, lengthT) {



		var idt = label + x;


		var element = new joint.shapes.html.Element({
			label: label,
			id: idt,
			size: {
				width: 272,
				height: 47
			},
			attrs: {
				ide: 'ide'
			},
			"width": 272,
			"height": 47,
			content: contentHtml

		});

		var getPosition = JSON.parse(localStorage.getItem($('#lifeCycle option:selected').text()));
		if (getPosition != null) {
			for (var pos in getPosition) {
				if (getPosition[pos].element == element.id) {
					console.debug("element= "  + getPosition[pos].element);
					element.get('position').x = getPosition[pos].left;
					element.get('position').y = getPosition[pos].top;

				}else {
					console.log("not equals");
				}

			}

		} else {
			element.get('position').x = x;
			element.get('position').y = y;
		}


		return element;
	}

	/**
	 *  create state of the graph 
	 *  
	 */
	function createElement(id,x,y) {
		var elm = new joint.shapes.erd.Entity({
			id: id,
			position: {
				x: x,
				y: y
			},
			attrs: {
				text: {
					text: id,
					fill: '#2e2e2e'
				}
			}

		});
		graph.addCell(elm);
		return elm;
	};

	/**
	 * 
	 *  create link beteween state and transition element 
	 */
	function createLinkById(sourceState, targetState) {

		var myLink = new erd.Line({
			source: {
				id: sourceState
			},
			target: {
				id: targetState
			},

			smooth: true,
			attrs: {

				'.marker-target': {
					d: 'M 10 0 L 0 5 L 10 10 z'
				}
			}
		});


		graph.addCell(myLink);
		return myLink;
	}

	/**
	 * 
	 *	get state from json data  
	 *  
	 *  
	 *  */

	function createState(states) {
		var statesArr = getAllStatesByTrans(states);
		var stateNoTrans = getAllStatesWhithNoTrans(states);

		var compteur = getNbStates(states);
		var posy = 0;
		var posMiddle = 0;
		var posLeft = 0;
		states.forEach(function(st,i){


			var getCookiePosition = JSON.parse(localStorage.getItem($('#lifeCycle option:selected').text()));

		
			if (getCookiePosition == null || compteur != states.length) {

				//createElement(st.id, posy+140, posy + 110);
				if($.inArray(st.id,statesArr) === -1 && ($.inArray(st.id,stateNoTrans) === -1)){
					createElement(st.id, 0, posy);
					console.log("etat non transition entrantes" + st.id);
					
				}else if($.inArray(st.id,statesArr) !== -1  && ($.inArray(st.id,stateNoTrans) !== -1)){
					createElement(st.id, 2000, posMiddle);
					console.log("etat non transition sortantes" + st.id);
					
				}else if($.inArray(st.id,statesArr) !== -1  && ($.inArray(st.id,stateNoTrans) === -1)){
					createElement(st.id, 660, posLeft);
					console.log("etat  transition sortantes et entrantes " + st.id);
				}else if($.inArray(st.id,statesArr) === -1 && ($.inArray(st.id,stateNoTrans) !== -1)){
					createElement(st.id, 2000, posMiddle);
					console.log("etat non transition entrantes" + st.id);
					
				}
				posy =  posy +150;
				posMiddle = posMiddle + 150;
				posLeft = posLeft + 150;

			} else {
				for (var pos in getCookiePosition) {
					if (st.id == getCookiePosition[pos].element)
						createElement(st.id, getCookiePosition[pos].left, getCookiePosition[pos].top);
				}
			}
		})
	}

	/**
	 * 
	 */

	function createTransition(states) {
		var x = 0;
		var y = 0;
		var transition;
		var statesArrT = getAllStatesByTrans(states);
		lengthT =  getyNbTransition(states);
		states.forEach(function(state, ide) {
			if (state.transitionMap != null) {
				$.each(state.transitionMap, function(item, trans) {
					
					if($.inArray(state.id,statesArrT) === -1){
						transition = createTemplateContentElementTransition(trans.descriptionT, trans.actionsList, state.id + '-' + trans.id + idCollapse++, 300, y,lengthT);
					}else
					{
						transition = createTemplateContentElementTransition(trans.descriptionT, trans.actionsList, state.id + '-' + trans.id + idCollapse++, 1200, y , lengthT);

					}
					x = x + 250;
					y = y + 150;

					graph.addCells([transition]);

					createLinkById(state.id, transition.id);
					trans.targetStates.forEach(function(element, index) {
						//console.debug(element);
						createLinkById(transition.id, element);

					})


				});
			}

		});
	}


	/** 
	 * Set a new localStorage and store positions of joint js generated elements 
	 * 
	 */
	function setLocalStorage(position) {

		var cposition = $('#lifeCycle option:selected').text();
		localStorage.setItem(cposition, JSON.stringify(position));

	}

	/**
	 * display localstorage content postions 
	 * 
	 */
	function getLocalStorage() {

		var getPosition = JSON.parse(localStorage.getItem($('#lifeCycle option:selected').text()));

	}

	/**
	 *  Display localStorage stored in cache 
	 * 
	 */
	function getCookie() {

		console.debug("recording positions from "+ $('#lifeCycle option:selected').text() +"  in progress  ");
	}

	/**
	 * event calls if we want to delete datas into localstorage(developer only)
	 */
	$("#delete").click(function(){
		localStorage.removeItem($('#lifeCycle option:selected').text());
		$("#model-text").text("Position supprimée pour le cycle de vie  : " + $('#lifeCycle option:selected').text());


	});

	/**
	 *  Return context path 
	 *  
	 */
	function getContextPath() {

		return window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
	}



	//	*** cookie **//
	/** 
	 * get positions of all elements of graph 
	 * 
	 */
	$("#record").click(function() {

		console.debug("record in progress");
		if (navigator.cookieEnabled) {
			console.debug("This browser accepts cookies");
		} else {
			alert("please enable  cookie to see the graph !");
		}

		var positionJson = {};
		$("*[model-id]").each(function(id, ide) {
			var i = "#j_" + id;
			var position = $(ide).offset();
			var posElt = {
					'left': position.left,
					'top': position.top,
					'element': $(ide).attr('model-id')
			};
			positionJson[i] = posElt;
		})


		var pos = JSON.stringify(positionJson);
		var getPosition = JSON.parse(pos);
		for (var pos in getPosition) {
			console.debug("left  " +  getPosition[pos].left + " , " + "top" + " " +  getPosition[pos].top);

		}


		setLocalStorage(positionJson);
		getLocalStorage();
		getCookie()

	});




	/**
	 * event handler on each graph's element , when an element is drag, this event is called
	 * 
	 * 
	 */
	function event(){
		graph.on('change:position', function(eventName, cell) {
			var positionJ = {};
			var posElt;

			if(arguments != null){
				if(arguments[0].attributes != undefined || arguments[0].attributes != null){

					$("*[model-id]").each(function(id, ide) {

						var i = "#j_" + id;
						var position = $(ide).offset();
						if(arguments[0].id == $(ide).attr('model-id') ){

							posElt = {
									'left': arguments[0].attributes.position.x,
									'top': arguments[0].attributes.position.y,
									'element': $(ide).attr('model-id')
							};
						}else{

							posElt = {
									'left': position.left,
									'top': position.top,
									'element': $(ide).attr('model-id')
							};

						}

						positionJ[i] = posElt;

					});
				}

			}



			setLocalStorage(positionJ);
			getLocalStorage();
			getCookie()
		});
	}

	/**
	 * return the number of states which are the same  stored in a localstorage variable and json 
	 */

	function getNbStates(states){
		var compteur = 0;
		var pos = localStorage.getItem($('#lifeCycle option:selected').text());
		var getPosition = JSON.parse(pos);

		var  p = eval( "(" + pos + ")");  

		for (var key in p) {
			states.forEach(function(s, ide) {
				if(s.id == p[key].element)

					compteur ++;
			});


		}


		return compteur;
	};


	/**
	 * return the number of transitions which are the same  stored in a localstorage variable and json data
	 */
	function getyNbTransition(states){

		var compteur = 0;
		var x = 0;
		var pos = localStorage.getItem($('#lifeCycle option:selected').text());


		var  p = eval( "(" + pos + ")");  

		tlength = 0;

		states.forEach(function(s, ide) {
			if (s.transitionMap != null) {
				s.transitionMap.forEach(function(transition,idt){

					tlength ++;
					var transitionN = transition.descriptionT + x;
					for (var key in p) {
						if(transitionN == p[key].element)
							compteur ++;
					}
					x = x + 250;
				})


			}
		})


		return compteur;
	};


	/**
	 * get the number of transitions presents in a lifecycle
	 */
	function getNbTransitionJson(states){
		states.forEach(function(s, ide) {
			if (s.transitionMap != null) {
				s.transitionMap.forEach(function(transition,idt){

					nbTransition ++;

				})
			}
		})


	}

	/** get state from all transitions **/
	function getAllStatesByTrans(states){
		var statesArray = [];
		var statesUniqueArray = [];
		states.forEach(function(state, ide) {

			if (state.transitionMap != null) {
				$.each(state.transitionMap, function(item, trans) {
					trans.targetStates.forEach(function(element, index) {


						statesArray.push(element);


					})


				});
			}

		});

		var unique = [];
		$.each(statesArray,function(i,el){
			if($.inArray(el,unique) === -1){
				unique.push(el);
			}
		})
		console.debug(unique);
		return statesArray;
	}

	/** get state from all transitions **/
	function getAllStatesWhithNoTrans(states){
		var statesArray = [];
		states.forEach(function(state, ide) {

			if (state.transitionMap === null) {
				statesArray.push(state.id);
			}

		});

		console.log(statesArray);
		return statesArray;
	}

	function compareArray(arr1, arr2){
		console.log("tableaux des etat ");
		console.log(arr2);
		console.log("tableaux des état cibles");
		console.log(arr1);
		var diff = arr2.filter(function (item, index) {
			return arr1.indexOf(item) !== arr1.lastIndexOf(item)
		});
		console.log(diff.join(" , "));
		return diff;
	}



});
