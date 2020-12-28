import { AfterViewInit, Component, ElementRef, Input, OnInit, Output, ViewChild, EventEmitter, OnChanges, SimpleChanges } from '@angular/core';
import { Observable } from 'rxjs';
import { CulturalOfferLocation, LocationRange } from '../../cultural-offer';

@Component({
  selector: 'app-offer-map',
  templateUrl: './offer-map.component.html',
  styleUrls: ['./offer-map.component.scss']
})
export class OfferMapComponent implements OnInit, AfterViewInit, OnChanges {

  @ViewChild("gmap", {read: ElementRef})
  gmap: ElementRef;

  @Input()
  culturalOffers: CulturalOfferLocation[];

  private mapInitialized: boolean = false;

  @Output()
  onMapBoundsChanged = new EventEmitter<LocationRange>();

  mapOptions: any;
  mapOverlays: any;
  mapInfoWindow: google.maps.InfoWindow;

  map: google.maps.Map;

  constructor() { }
  ngOnChanges(changes: SimpleChanges): void {
    if (this.mapInitialized)
      this.updateCulturalOfferLocations();
  }

  ngAfterViewInit(): void {
    console.log(this.gmap.nativeElement);
  }

  setMap(event) {
    this.map = event.map;
  }

  ngOnInit(): void {
    this.mapOptions = {};
    this.mapInfoWindow = new google.maps.InfoWindow();
    this.updateCulturalOfferLocations();
    let bounds = new google.maps.LatLngBounds();
    this.mapOverlays.forEach(marker => {
      bounds.extend(marker.getPosition());
    });

    setTimeout(()=> {
      this.map.fitBounds(bounds);
    }, 1000);
    this.mapInitialized = true;
  }

  updateCulturalOfferLocations(): void { 
    this.mapOverlays = this.culturalOffers.map((culturalOffer) => 
      new google.maps.Marker({
        position: {lat: culturalOffer.location.latitude, lng:culturalOffer.location.longitude },
        title: culturalOffer.name
      })
    );
  }

  handleOverlayClick(event: any): void {
    let isMarker = event.overlay.getTitle != undefined;

    if (isMarker) {
      let title = event.overlay.getTitle();
      this.mapInfoWindow.setContent(title);
      this.mapInfoWindow.open(event.map, event.overlay);
    }
  }
  // salje se dva put pri ucitavanju komponentre, resi
  mapBoundsChanged(): void {
    if (!this.mapInitialized)
      return;
    let bounds: google.maps.LatLngBounds = this.map.getBounds();
    let locationRange: LocationRange = {
      latitudeFrom: bounds.getSouthWest().lat(),
      latitudeTo: bounds.getNorthEast().lat(),
      longitudeFrom: bounds.getSouthWest().lng(),
      longitudeTo: bounds.getNorthEast().lng()
    };
    this.onMapBoundsChanged.emit(locationRange);
  }

}
