/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var map;
var selectedMarkers = [];
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
        disableDoubleClickZoom: true, // disable the default map zoom on double click
    });

    // Create new marker on single click event on the map
//    google.maps.event.addListener(map, 'click', function (event) {
//        var marker = new google.maps.Marker({
//            position: event.latLng,
//            map: map,
//            title: event.latLng.lat() + ', ' + event.latLng.lng()
//        });
//
//        selectedMarkers.push(marker);

//        var content = document.getElementById('form:fname').value;
//        document.getElementById('form:fname').value = content + event.latLng.lat() + "," + event.latLng.lng() + "\n\n";
//        
//        listCoordinates.push([event.latLng.lat(),event.latLng.lng()]);
//        document.getElementById('form:json').value = JSON.stringify(listCoordinates);
//    });

    var input = document.getElementById('input-gg-map');
    var searchBox = new google.maps.places.SearchBox(input);

    // Bias the SearchBox results towards current map's viewport.
    map.addListener('bounds_changed', function () {
        searchBox.setBounds(map.getBounds());
    });

    var markers = [];
    // Listen for the event fired when the user selects a prediction and retrieve
    // more details for that place.
    searchBox.addListener('places_changed', function () {
        var places = searchBox.getPlaces();

        if (places.length == 0) {
            return;
        }

        // Clear out the old markers.
        markers.forEach(function (marker) {
            marker.setMap(null);
        });
        markers = [];

        // For each place, get the icon, name and location.
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

            // Create a marker for each place.
//            markers.push(new google.maps.Marker({
//                map: map,
//                icon: icon,
//                title: place.name,
//                position: place.geometry.location
//            }));

            if (place.geometry.viewport) {
                // Only geocodes have viewport.
                bounds.union(place.geometry.viewport);
            } else {
                bounds.extend(place.geometry.location);
            }
        });
        map.fitBounds(bounds);
    });
}
var dataLayer;
function drawDataRoadByJSon() {
    let json = $('#geojson').val();
    if (dataLayer != null)
    for (let i=0; i<dataLayer.length; i++){
        map.data.remove(dataLayer[i]);
    }
    dataLayer = map.data.addGeoJson(JSON.parse(json));
    map.data.addListener('click', function(event){
        console.log(event);
        $('#form-detail\\:landNearRoadID').val(event.feature.getProperty('id'));
        document.getElementById("landNearRoadName").innerHTML = event.feature.getProperty('name');
        document.getElementById("landNearRoadAveragePrice").innerHTML = event.feature.getProperty('averagePrice');
        //document.getElementById("landNearRoadPredictPrice").innerHTML = event.feature.getProperty('predictPrice');
        document.getElementById("landNearRoadMinPrice").innerHTML = event.feature.getProperty('minPrice');
        document.getElementById("landNearRoadMaxPrice").innerHTML = event.feature.getProperty('maxPrice');
        document.getElementById("form-detail:btnSendLandNearRoadID").click();
    });
}

