import { AfterViewInit, Component, ElementRef, Input, OnInit, Output, ViewChild, EventEmitter, OnChanges, SimpleChanges, SimpleChange } from '@angular/core';
import { CulturalOfferLocation, LocationRange } from 'src/app/modules/cultural-offers/cultural-offer';

@Component({
  selector: 'app-offer-map',
  templateUrl: './offer-map.component.html',
  styleUrls: ['./offer-map.component.scss']
})
export class OfferMapComponent implements OnInit, AfterViewInit, OnChanges {

  @ViewChild('gmap', {read: ElementRef})
  gmap: ElementRef;

  @Input()
  culturalOffers: CulturalOfferLocation[];

  @Input()
  bounds: LocationRange;

  private mapInitialized = false;
  private shouldEmitBoundsChanged = true;

  @Output()
  mapBoundsChanged = new EventEmitter<LocationRange>();

  mapOptions: any;
  mapOverlays: google.maps.Marker[];
  mapInfoWindow: google.maps.InfoWindow;
  map: google.maps.Map;

  constructor() { }

  ngOnInit(): void {
    this.mapOptions = {};
    this.mapInfoWindow = new google.maps.InfoWindow();

    this.updateCulturalOfferLocations();
  }

  ngAfterViewInit(): void {
    this.mapInitialized = true;
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.mapInitialized) {
      if (changes.culturalOffers) {
        this.updateCulturalOfferLocations();
      }
      if (changes.bounds) {
        this.updateMapBounds();
      }
    }
  }

  updateMapBounds(): void {
    this.shouldEmitBoundsChanged = false;

    const sw: google.maps.LatLng = new google.maps.LatLng(this.bounds.latitudeFrom, this.bounds.longitudeFrom);
    const ne: google.maps.LatLng = new google.maps.LatLng(this.bounds.latitudeTo, this.bounds.longitudeTo);
    const bounds: google.maps.LatLngBounds = new google.maps.LatLngBounds(sw, ne);
    if (!this.map.getBounds() || !bounds.equals(this.map.getBounds())) {
      this.map.fitBounds(bounds);
    }

    this.shouldEmitBoundsChanged = true;
  }

  setMap(event: any): void {
    this.map = event.map;
    this.updateMapBounds();
  }

  updateCulturalOfferLocations(): void {
    this.mapOverlays = this.culturalOffers.map((culturalOffer) => {
      return new google.maps.Marker({
        position: {lat: culturalOffer.location.latitude, lng: culturalOffer.location.longitude },
        title: culturalOffer.name
      });
    });
  }

  onMapBoundsChanged(): void {
    if (!this.mapInitialized || !this.shouldEmitBoundsChanged) {
      return;
    }

    const bounds: google.maps.LatLngBounds = this.map.getBounds();
    const locationRange: LocationRange = {
      latitudeFrom: bounds.getSouthWest().lat(),
      latitudeTo: bounds.getNorthEast().lat(),
      longitudeFrom: bounds.getSouthWest().lng(),
      longitudeTo: bounds.getNorthEast().lng()
    };
    this.mapBoundsChanged.emit(locationRange);
  }

  handleOverlayClick(event: any): void {
    const isMarker: boolean = event.overlay.getTitle !== undefined;

    if (!isMarker) {
      return;
    }

    const title: string = event.overlay.getTitle();
    const latLng: google.maps.LatLng = event.originalEvent.latLng;
    const offer: CulturalOfferLocation = this.culturalOffers.filter((culturalOffer) => culturalOffer.location.latitude === latLng.lat() &&
      culturalOffer.location.longitude === latLng.lng())[0];

    this.mapInfoWindow.setContent(`<h3>${title}</h3><p>${offer.location.address}</p><a pButton href='/cultural-offers/${offer.id}'>View page<a>`);
    this.mapInfoWindow.open(event.map, event.overlay);
  }

}
