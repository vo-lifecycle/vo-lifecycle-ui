/**
 * 
 * @author anthony attia <anthony.attia1@gmail.com>
 *
 */
$(function() {

    //Get Json format lifecycle datas from an other servlet  
    var url = getContextPath() + "/GenerateJsonLifeCycleServlet?action=showList";
    var globalGraph;
    var graph = null;
    $.ajax(url, {
        type: 'GET', // it's easier to read GET request parameters
        dataType: 'json',
        async: true,
        success: function(data) {
            if (data != null) {
                console.debug(data.length);
                data.list.forEach(function(status, index) {
                    $("#lifeCycle").append($('<option>', {
                        value: index,
                        text: status.description
                    }));
                })

            } else {
                console.debug("data is null");
            }

        },
        error: function(xhr, ajaxOptions, thrownError) {
            console.error(xhr.status, thrownError);
        }
    })

    //Generate graph corresponding to the lifeCycle choosed
    $("#submitForm").submit(function(event) {



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

                    //localStorage.removeItem($('#lifeCycle option:selected').text());
                    if (graph != null) {
                        graph.clear();
                    }
                    createState(data.state);
                    createTransition(data.state);

                } else {
                    console.debug("data is null");
                }

            },
            error: function(xhr, ajaxOptions, thrownError) {
                console.error(xhr.status, thrownError);
            }
        })


        graph = new joint.dia.Graph;
        globalGraph = graph;


       
        event.preventDefault();


        var paper = new joint.dia.Paper({
            el: $('#paper'),
            width: 1500,
            height: 2000,
            gridSize: 10,
            model: graph
        });
        var erd = joint.shapes.erd;

        var uml = joint.shapes.uml;

        var idCollapse = 0;

        var x = 0;
        var y = 0;

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




        function createTemplateContentElementTransition(label, listAction, id, x, y) {
            //arrayChecker = ["check 1", "check 2"];
            var html = '<button class="delete btn btn-success" type="button" data-toggle="collapse" data-target="#collapse' + id + '" aria-expanded="false"        aria-,' + 'controls="collapseExample">!</button>' + '<a href="#" class="btn btn-info btn-lg"><label>' + label + '</label>'

            +'</a>' + '<div id= "collapse' + id + '" class="collapse blue" aria-expanded="false" style="background-color: white; width:180px;">'
            if (listAction != null) {
                listAction.forEach(function(action, index) {
                    html += "<p>" + action.description + "</p>"
                })
            } + '<text></text>' + '<span></span><br/>' + '</div>';
            //console.debug(arrayChecker);
            var elementTransition = createElementTransition(html, label, x, y);
            return elementTransition;

        }


        //** create transition element with an html template **//
        function createElementTransition(contentHtml, label, x, y) {

            var idt = label + x;
            console.debug(idt);

            var element = new joint.shapes.html.Element({
                label: label,
                id: idt,
                size: {
                    width: 170,
                    height: 28
                },
                attrs: {
                    ide: 'ide'
                },
                "width": 170,
                "height": 28,
                content: contentHtml

            });
            var getPosition = JSON.parse(localStorage.getItem($('#lifeCycle option:selected').text()));
            if (getPosition != null) {
                for (var pos in getPosition) {
                    if (getPosition[pos].element == element.id) {
                        console.debug("element id = " + element.id + "sa position x = " + getPosition[pos].left);
                        console.debug("element id = " + element.id + "sa position y = " + getPosition[pos].top);


                        element.get('position').x = getPosition[pos].left;
                        element.get('position').y = getPosition[pos].top;

                    }
                }

            } else {
                element.get('position').x = Math.random() * (500) + 1;
                element.get('position').y = Math.random() * (200) + 1;
            }


            return element;
        }

        //** create state of the graph **//
        function createElement(id,x,y) {
            var elm = new joint.shapes.erd.Entity({
                id: id,
                position: {
                    x: x,
                    y: y
                },
                attrs: {
                    text: {
                        text: id
                    }
                }

            });
            graph.addCell(elm);
            return elm;
        };

        //** create link beteween state and transition element **//
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

        //*** get state from json data  ***//
        var xs = 10;
        var ys = 600;

        function createState(states) {
            states.forEach(function(s, ide) {

                var getCookiePosition = JSON.parse(localStorage.getItem($('#lifeCycle option:selected').text()));
                if (getCookiePosition == null) {
                    createElement(s.id, xs + 100, ys + 100);
                    console.debug("cookie does not exist yet");
                } else {
                    for (var pos in getCookiePosition) {
                        if (s.id == getCookiePosition[pos].element)
                            createElement(s.id, getCookiePosition[pos].left, getCookiePosition[pos].top);
                    }
                }
            });
        }

        function createTransition(states) {
            states.forEach(function(state, ide) {
                if (state.transitionMap != null) {
                    $.each(state.transitionMap, function(item, trans) {
                        var transition = createTemplateContentElementTransition(trans.descriptionT, trans.actions, state.id + '-' + trans.id + idCollapse++, x, y);
                        x = x + 250;
                        y = y + 100;

                        graph.addCells([transition]);

                        createLinkById(state.id, transition.id);
                        trans.targetStates.forEach(function(element, index) {
                            console.debug(element);
                            createLinkById(transition.id, element);

                        })


                    });
                }

            });
        }
    });

  
    //*** cookie **//
    //get positions of all elements of graph
    $("#record").click(function() {

        console.debug("record in progress");
        if (navigator.cookieEnabled) {
            console.debug("This browser accepts cookies");
        } else {
            alert("please enable  cookie to see the graph !");
        }

        var positionJson = {};


        var positionJson = {};
        $("*[model-id]").each(function(id, ide) {
            var i = "#j_" + id;
            var position = $(ide).position();
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


    // Set a new localStorage and store positions of joint js generated elements
    function setLocalStorage(position) {

        var cposition = $('#lifeCycle option:selected').text();
        localStorage.setItem(cposition, JSON.stringify(position));
        //console.debug(JSON.stringify(position));
    }

    //display localstorage content postions
    function getLocalStorage() {

        var getPosition = JSON.parse(localStorage.getItem($('#lifeCycle option:selected').text()));
        
    }
    
    //Display localStorage stored in cache
    function getCookie() {
        
        var i;

        console.debug("local storage");
        for (i = 0; i < localStorage.length; i++) {
            console.debug(localStorage.key(i) + "=[" + localStorage.getItem(localStorage.key(i)) + "]");
        }

        
    }

    //Return context path
    function getContextPath() {

        return window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
    }
});