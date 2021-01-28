"use strict";

const months = [
  "January",
  "February",
  "March",
  "April",
  "May",
  "June",
  "July",
  "August",
  "September",
  "October",
  "November",
  "December",
];

//Elements
const form = document.querySelector(".form");
const containerWorkouts = document.querySelector(".workouts");
const inputType = document.querySelector(".form__input--type");
const inputDistance = document.querySelector(".form__input--distance");
const inputDuration = document.querySelector(".form__input--duration");
const inputCadence = document.querySelector(".form__input--cadence");
const inputElevation = document.querySelector(".form__input--elevation");
const mapZoomLevel = 16;
let map, mapEvent;

//Parent Workout class
class Workout {
  date = new Date();
  id = Date.now().toString().slice(-10);
  clicks = 0;

  constructor(coords, distance, duration) {
    this.coords = coords;
    this.distance = distance;
    this.duration = duration;
  }

  _createDescription() {
    this.description = `${this.type[0].toUpperCase()}${this.type.slice(1)} on ${
      months[this.date.getMonth()]
    } ${this.date.getDate()}`;
  }

  click() {
    this.clicks++;
  }
}

//Child classes of the Workout class
class Running extends Workout {
  type = "running";

  constructor(coords, distance, duration, cadence) {
    super(coords, distance, duration);
    this.cadence = cadence;
    this._calcPace();
    this._createDescription();
  }

  _calcPace() {
    // min/km
    this.pace = this.duration / this.distance;
    return this.pace;
  }
}

class Cycling extends Workout {
  type = "cycling";

  constructor(coords, distance, duration, elevationGain) {
    super(coords, distance, duration);
    this.elevationGain = elevationGain;
    this._calcSpeed();
    this._createDescription();
  }

  _calcSpeed() {
    // min/km
    this.speed = this.distance / (this.duration / 60);
    return this.speed;
  }
}

//Class containing the main app logic
class App {
  #map;
  #mapEvent;
  #workouts = [];

  constructor() {
    this._getPosition();
    form.addEventListener("submit", this._newWorkout.bind(this));
    inputType.addEventListener("change", this._toggleElevationField);
    containerWorkouts.addEventListener(
      "click",
      this._moveToWorkoutMarker.bind(this)
    );
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
    this.#map = L.map("map").setView(coordinates, mapZoomLevel);

    L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
      attribution:
        '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
    }).addTo(this.#map);

    //Event handler for click on the map
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
    //Helper methods to validate input
    const validateNumbers = (...numbers) =>
      numbers.every((n) => Number.isFinite(n));
    const validatePositive = (...numbers) => numbers.every((n) => n > 0);

    //Get user input from the form
    const type = inputType.value;
    const distance = Number(inputDistance.value);
    const duration = Number(inputDuration.value);

    const { lat, lng } = this.#mapEvent.latlng;
    const coords = [lat, lng];
    let workout;

    //Running workout case
    if (type === "running") {
      const cadence = Number(inputCadence.value);

      //Validate data
      if (
        !validateNumbers(distance, duration, cadence) ||
        !validatePositive(distance, duration, cadence)
      ) {
        this._resetInputFields();
        return alert(
          "All fields must be filled and all inputs must be positive!"
        );
      }

      workout = new Running(coords, distance, duration, cadence);
    }

    //Cycling workout case
    if (type === "cycling") {
      const elevationGain = Number(inputElevation.value);

      //Validate data
      if (
        !validateNumbers(distance, duration, elevationGain) ||
        !validatePositive(distance, duration)
      ) {
        this._resetInputFields();
        return alert(
          "All fields must be filled and all inputs must be positive!"
        );
      }

      workout = new Cycling(coords, distance, duration, elevationGain);
    }

    //Add new object to workouts array
    this.#workouts.push(workout);

    //Render workout on the workout list
    this._addWorkoutToTheList(workout);

    //Render workout on the map as a marker
    this._renderWorkoutMarker(workout);

    //Reset input fields
    this._resetInputFields();
  }

  //Method to display workout marker on the map
  _renderWorkoutMarker(workout) {
    L.marker(workout.coords)
      .addTo(this.#map)
      .bindPopup(
        L.popup({
          maxWidth: 250,
          minWidth: 100,
          autoClose: false,
          closeOnClick: false,
          className: `${workout.type}-popup`,
        })
      )
      .setPopupContent(
        `${workout.type === "running" ? "🏃‍♂️" : "🚴‍♀️"} ${workout.description}`
      )
      .openPopup();
  }

  //Method to move your view to the marker of the clicked workout on the map
  _moveToWorkoutMarker(e) {
    const workoutEl = e.target.closest(".workout");

    if (!workoutEl) return;

    const currWorkout = this.#workouts.find(
      (w) => w.id === workoutEl.dataset.id
    );

    this.#map.setView(currWorkout.coords, mapZoomLevel, {
      pan: { duration: 1, animate: true },
    });

    currWorkout.click();
  }

  //Method to add workouts to the workout list
  _addWorkoutToTheList(workout) {
    let elementHtml = `
    <li class="workout workout--${workout.type}" data-id=${workout.id}>
      <h2 class="workout__title">${workout.description}</h2>
      <div class="workout__details">
        <span class="workout__icon">${
          workout.type === "running" ? "🏃‍♂️" : "🚴‍♀️"
        }</span>
        <span class="workout__value">${workout.distance}</span>
        <span class="workout__unit">km</span>
      </div>
      <div class="workout__details">
        <span class="workout__icon">⏱</span>
        <span class="workout__value">${workout.duration}</span>
        <span class="workout__unit">min</span>
      </div>
    `;

    if (workout.type === "running") {
      elementHtml += `
          <div class="workout__details">
            <span class="workout__icon">⚡️</span>
            <span class="workout__value">${workout.pace.toFixed(1)}</span>
            <span class="workout__unit">min/km</span>
          </div>
          <div class="workout__details">
            <span class="workout__icon">🦶🏼</span>
            <span class="workout__value">${workout.cadence}</span>
            <span class="workout__unit">spm</span>
          </div>
        </li>
      `;
    } else {
      elementHtml += `
          <div class="workout__details">
            <span class="workout__icon">⚡️</span>
            <span class="workout__value">${workout.speed.toFixed(1)}</span>
            <span class="workout__unit">km/h</span>
          </div>
          <div class="workout__details">
            <span class="workout__icon">⛰</span>
            <span class="workout__value">${workout.elevationGain}</span>
            <span class="workout__unit">m</span>
          </div>
        </li>
      `;
    }

    form.insertAdjacentHTML("afterend", elementHtml);
  }

  //Method to reset all input fields
  _resetInputFields() {
    inputDistance.value = inputCadence.value = inputDuration.value = inputElevation.value = inputCadence.value =
      "";
    inputElevation.blur();
    inputCadence.blur();
    //Hide form
    this._hideForm();
  }

  //Method to hide the form
  _hideForm() {
    form.style.display = "none";
    form.classList.add("hidden");
    setTimeout(function () {
      form.style.display = "grid";
    }, 1000);
  }
}

const app = new App();
