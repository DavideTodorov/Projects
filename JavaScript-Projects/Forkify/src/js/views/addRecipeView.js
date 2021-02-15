import View from "./view.js";
import icons from "url:../../img/icons.svg";

class AddRecipeView extends View {
  _parentElement = document.querySelector(".upload");
  _defaultMessage = "The recipe was added successfully";
  _window = document.querySelector(".add-recipe-window");
  _overlay = document.querySelector(".overlay");
  _buttonOpen = document.querySelector(".nav__btn--add-recipe");
  _buttonClose = document.querySelector(".btn--close-modal");
  _uploadButton = document.querySelector(".upload__btn");

  constructor() {
    super();
    this._addButtonOpenHandler();
    this._addCloseHandler();
  }

  //Method to show or hide the add recipe window
  toggleUIDisplay() {
    this._window.classList.toggle("hidden");
    this._overlay.classList.toggle("hidden");
  }

  //Handler to open recipe window
  _addButtonOpenHandler() {
    this._buttonOpen.addEventListener(
      "click",
      this.toggleUIDisplay.bind(this)
    );
  }

  //Handler to close recipe window
  _addCloseHandler() {
    this._buttonClose.addEventListener(
      "click",
      this.toggleUIDisplay.bind(this)
    );
    this._overlay.addEventListener("click", this.toggleUIDisplay.bind(this));
  }

  //Handler for uploading the new recipe
  addUploadHandler(handler) {
    this._parentElement.addEventListener("submit", function (e) {
      e.preventDefault();

      const dataArr = [...new FormData(this)];
      const data = Object.fromEntries(dataArr);
      handler(data);
    });
  }

  _generateMarkup() {}
}

export default new AddRecipeView();
