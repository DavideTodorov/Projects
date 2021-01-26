"use strict";

// prettier-ignore
const months = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];

//Elements
const form = document.querySelector(".form");
const containerWorkouts = document.querySelector(".workouts");
const inputType = document.querySelector(".form__input--type");
const inputDistance = document.querySelector(".form__input--distance");
const inputDuration = document.querySelector(".form__input--duration");
const inputCadence = document.querySelector(".form__input--cadence");
const inputElevation = document.querySelector(".form__input--elevation");
let map, mapEvent;

class App {
  #map;
  #mapEvent;

  constructor() {
    this._getPosition();
    form.addEventListener("submit", this._newWorkout.bind(this));
    inputType.addEventListener("change", this._toggleElevationField);
  }

  //Method to determine current position
  _getPosition() {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        //Success case
        this._loadMap.bind(this),
        //Fail case
        function () {
          alert(
            "Could not load the map! Please check your privacy settings and reload the page!"
          );
        }
      );
    }
  }

  //Method to invoke the form and wait for click on the map
  _loadMap(position) {
    const latitude = position.coords.latitude;
    const longitude = position.coords.longitude;
    const coordinates = [latitude, longitude];
    this.#map = L.map("map").setView(coordinates, 16);

    L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
      attribution:
        '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
    }).addTo(this.#map);

    this.#map.on("click", this._showForm.bind(this));
  }

  //Method to display form
  _showForm(mapE) {
    //mapE contains the coordinates of the pressed location
    this.#mapEvent = mapE;

    form.classList.remove("hidden");
    inputDistance.focus();
  }

  //Method to switch between elevation and cadence
  _toggleElevationField() {
    inputElevation.closest(".form__row").classList.toggle("form__row--hidden");
    inputCadence.closest(".form__row").classList.toggle("form__row--hidden");
  }

  //Method to create the new workout and add marker on the map
  _newWorkout(e) {
    e.preventDefault();

    //Coords of the current pressed location
    const { lat, lng } = this.#mapEvent.latlng;

    //Add marker
    L.marker([lat, lng])
      .addTo(this.#map)
      .bindPopup(
        L.popup({
          maxWidth: 250,
          minWidth: 100,
          autoClose: false,
          closeOnClick: false,
          className: "leaflet-popup",
        })
      )
      .setPopupContent("You are here")
      .openPopup();

    //Reset input fields
    inputDistance.value = inputCadence.value = inputDuration.value = inputElevation.value = inputCadence.value =
      "";
    inputElevation.blur();
    inputCadence.blur();
  }
}

const app = new App();
