window.google = {
	maps: {
		LatLng: function(lat, lng) {
			return {
				latitude: parseFloat(lat),
				longitude: parseFloat(lng),

				lat: function() { return this.latitude; },
				lng: function() { return this.longitude; }
			};
		},
		LatLngBounds: function(ne, sw) {
			return {
				getSouthWest: function() { 
					return {
						lat: function() { return sw.lat; },
						lng: function() { return sw.lng; }
					}; 
				},
				getNorthEast: function() { 
					return {
						lat: function() { return ne.lat; },
						lng: function() { return ne.lng; }
					}; 
				},
				equals: function(other) {
					console.log(other);
					return ne.lat == other.getNorthEast().lat &&
							ne.lng == other.getNorthEast().lng &&
							sw.lat == other.getSouthWest().lat &&
							sw.lng == other.getSouthWest().lat;
				}
			};
		},
		OverlayView: function() {
			return {};
		},
		InfoWindow: function() {
			return {
				content: '',
				setContent: function(str) { this.content = str },
				getContent: function() { return this.content; },
				open: function(map, overlay) {}
			};
		},
		Marker: function() {
			return {};
		},
		MarkerImage: function() {
			return {};
		},
		Map: function() {
			return {};
		},
		Point: function() {
			return {};
		},
		Size: function() {
			return {};
		}
	}
};