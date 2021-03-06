

var map;
var selectedMarkers = [];
var deleteOld = true;
var path = [];
var searchBox;
var saveRow = [];
function initMap() {
    //FPT 
    var latitude = 21.012633;
    var longitude = 105.527423;

    var myLatLng = {lat: latitude, lng: longitude};

    map = new google.maps.Map(document.getElementById('map'), {
        center: myLatLng,
        zoom: 14,
        mapTypeId: 'roadmap',
        clickableIcons: false,
        disableDoubleClickZoom: true,
        disableDefaultUI: true,
        fullscreenControl: false
    });
    map.addListener('bounds_changed', function() {
      searchBox.setBounds(map.getBounds());
    });

    var formControl = $('#formControl')[0];
    map.controls[google.maps.ControlPosition.TOP_RIGHT].push(formControl);
    
    var markers = [];
    var input = $('#searchbox-Address')[0];
    
    searchBox = new google.maps.places.SearchBox(input);

    map.addListener('bounds_changed', function () {
        searchBox.setBounds(map.getBounds());
    });

    var markers = [];

    searchBox.addListener('places_changed', function () {
        var places = searchBox.getPlaces();

        if (places.length == 0) {
            return;
        }

        markers.forEach(function (marker) {
            marker.setMap(null);
        });
        markers = [];

        var bounds = new google.maps.LatLngBounds();
        places.forEach(function (place) {
            if (!place.geometry) {
                console.log("Returned place contains no geometry");
                return;
            }
            var icon = {
                url: place.icon,
                size: new google.maps.Size(71, 71),
                origin: new google.maps.Point(0, 0),
                anchor: new google.maps.Point(17, 34),
                scaledSize: new google.maps.Size(25, 25)
            };
            if (place.geometry.viewport) {
                bounds.union(place.geometry.viewport);
            } else {
                bounds.extend(place.geometry.location);
            }
        });
        map.fitBounds(bounds);
    });

    google.maps.event.addListener(map, 'click', function (event) {
    	let selectedType = $("#form\\:cbb-IpgType option:selected").val();
    	
    	let marker = new google.maps.Marker({
            position: event.latLng,
            map: map,
            title: event.latLng.lat() + ', ' + event.latLng.lng()
        });
    	
    	
    	
    	if (deleteOld){
    		clearOldMarkers();
        	setCoordinateForInput(marker);
        	selectedMarkers.push(marker);
    	}else{
    		selectedMarkers.push(marker);
    		addNewRowCoordinate();
    		addDataToNewRow(marker);
    	}

    });
}
let countRow = 0;
function addDataToNewRow(marker){
	$('#lng-'+(countRow-1)).val(marker.getPosition().lng());
	$('#lat-'+(countRow-1)).val(marker.getPosition().lat());
	$('#form\\:txtInput_multipleCoordinate').val(JSON.stringify(
			selectedMarkers.map(x=>{
				let obj={};obj.latitude = x.getPosition().lat();obj.longitude=x.getPosition().lng();
				return obj})
		));
}
function addNewRowCoordinate(){
	$('#div-multiple').show();
	var newRow = $("<tr>");
    var cols = "";
	cols+= '<td class="td-lng"><input type="text" readonly name="longitude" id="lng-'+countRow+'" class="form-control longitude-multi""/></td>';
	cols+= '<td class="td-lat"><input type="text" readonly name="latitude"  id="lat-'+countRow+'" class="form-control latitude-multi"/></td>';
	countRow++;
	cols += '<td><input type="button" class="ibtnDel btn btn-md btn-danger" onclick="deleteRow(this)" value="X"></td>';
    newRow.append(cols);
    saveRow.push(newRow);
    $("#table-coordinate").append(newRow);
}
function renderTable(){
	saveRow.forEach(x=>{
		$("#table-coordinate").append(x);
	});
}
function deleteRow(element){
	console.log($(element).closest("tr"));
	$(element).closest("tr").remove();
	let lng = $($(element).parent().parent().children()[0]).children().val()
	let lat = $($(element).parent().parent().children()[1]).children().val()
	saveRow = saveRow.filter(x=>{
		return ($($($($(x).children())[0].children)[0]).val() != lng && $($($($(x).children())[1].children)[0]).val() != lat);
	});
	selectedMarkers = selectedMarkers.filter(x=> {
		if (x.getPosition().lat()==lat && x.getPosition().lng()==lng){
			x.setMap(null);
		}
		return x.getPosition().lat()!=lat && x.getPosition().lng()!=lng
	});
	$('#form\\:txtInput_multipleCoordinate').val(JSON.stringify(
		selectedMarkers.map(x=>{
			let obj={};obj.latitude = x.getPosition().lat();obj.longitude=x.getPosition().lng();
			return obj}))
	);	
	
	
	
	countRow--;
}
$("#table-coordinate").on("click", ".ibtnDel", function (event) {
    $(this).closest("tr").remove();       
    countRow--;
});

function updateDeleteOld(){
	let selectedType = $("#form\\:cbb-IpgType option:selected").val();
	if (selectedType == "4"){
		deleteOld = false;
	}else{
		deleteOld = true;
	}
	countRow = 0;
}
function setCoordinateForInput(marker){
	$('#form\\:txtinput-lngSingleCoordinate').val(marker.getPosition().lng());
	$('#form\\:txtinput-latSingleCoordinate').val(marker.getPosition().lat());
}

function clearOldMarkers(){
	for (let i=0; i<selectedMarkers.length; i++){
        selectedMarkers[i].setMap(null);
    }
    selectedMarkers = [];
}

function drawPath(){
	let fromJson = JSON.parse($('#form\\:txtInput_multipleCoordinate').val());
	let line = fromJson.map(x=>{
		let o={};
		o.lat=x.latitude;
		o.lng=x.longitude;
		return o;
	});
	let element = new google.maps.Polyline({
	    path: line,
	    geodesic: true,
	    strokeColor: '#FF0000',
	    strokeOpacity: 1.0,
	    strokeWeight: 8
	  });
	element.setMap(map);
	path.push(element);
}

function focusMap(latitude, longitude, zoom){
    map.setCenter(new google.maps.LatLng(latitude, longitude));
    map.setZoom(zoom);
}
function changeInfo(name, longitude, latitude){
	$('#form\\:txtinput-Name').val(name);
	$('#form\\:txtinput-lngSingleCoordinate').val(longitude);
	$('#form\\:txtinput-latSingleCoordinate').val(latitude);
}
function clearDataMap(){
	path.forEach(x=>x.setMap(null));
	selectedMarkers.forEach(x=>x.setMap(null));
	path = [];
	selectedMarkers = [];
}
function clearAllInput(){
	
	$('#form\\:txtinput-Name').val("");
	$('#form\\:txtinput-lngSingleCoordinate').val("");
	$('#form\\:txtinput-latSingleCoordinate').val("");
}