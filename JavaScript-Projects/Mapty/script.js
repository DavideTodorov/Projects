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

//======================================================
//Display the map using Leaflet library and Geolocation
if (navigator.geolocation) {
  navigator.geolocation.getCurrentPosition(
    //Success function
    function (position) {
      //Coordinates
      const latitude = position.coords.latitude;
      const longitude = position.coords.longitude;
      const coordinates = [latitude, longitude];
      const map = L.map("map").setView(coordinates, 16);

      //The map
      L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
        attribution:
          '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
      }).addTo(map);

      //Initial marker on the map with your current location
      L.marker(coordinates)
        .addTo(map)
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

      //Display a marker on the map when you click a certain position
      map.on("click", function (mapEvent) {
        const { lat, lng } = mapEvent.latlng;

        L.marker([lat, lng])
          .addTo(map)
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
      });
    },
    //Error function
    function () {
      alert(
        "Could not load the map! Please check your privacy settings and reload the page!"
      );
    }
  );
}
