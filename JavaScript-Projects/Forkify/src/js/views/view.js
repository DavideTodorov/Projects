import icons from "url:../../img/icons.svg";

export default class View {
  _data;

  //Method to render recipe
  render(recipeData) {
    //Return if the recipeData is empty
    if (!recipeData || (Array.isArray(recipeData) && recipeData.length === 0)) {
      console.log("Error  ");
      return this.renderError();
    }

    this._data = recipeData;
    const markup = this._generateMarkup();
    this._clear();
    this._parentElement.insertAdjacentHTML("afterbegin", markup);
  }

  //Method to partly change the existing markup
  update(data) {
    this._data = data;
    const newMarkup = this._generateMarkup();
    const newDOM = document.createRange().createContextualFragment(newMarkup);
    const newElements = Array.from(newDOM.querySelectorAll("*"));
    const curElements = Array.from(this._parentElement.querySelectorAll("*"));

    for (let i = 0; i < newElements.length; i++) {
      const curEl = curElements[i];
      const newEl = newElements[i];

      //Update changed text
      if (
        !newEl.isEqualNode(curEl) &&
        newEl.firstChild?.nodeValue.trim() !== ""
      ) {
        curEl.textContent = newEl.textContent;
      }

      //Update changed attributes
      if (!newEl.isEqualNode(curEl)) {
        Array.from(newEl.attributes).forEach((attr) =>
          curEl.setAttribute(attr.name, attr.value)
        );
      }
    }
  }

  //Method to clear the parent element
  _clear() {
    this._parentElement.innerHTML = "";
  }

  //Method to render the spinner
  renderSpinner() {
    const markup = `
              <div class="spinner">
                <svg>
                  <use href="${icons}#icon-loader"></use>
                </svg>
              </div>
        `;
    this._parentElement.innerHTMl = "";
    this._parentElement.insertAdjacentHTML("afterbegin", markup);
  }

  //Method to render errors on the UI
  renderError(message = this._errorMessage) {
    const markup = `
          <div class="error">
            <div>
              <svg>
                <use href="${icons}#icon-alert-triangle"></use>
              </svg>
            </div>
            <p>${message}</p>
          </div>
    `;

    this._clear();
    this._parentElement.insertAdjacentHTML("afterbegin", markup);
  }

  //Method to render a message on the UI
  renderMessage(message = this._defaultMessage) {
    const markup = `
        <div class="message">
          <div>
            <svg>
              <use href="${icons}#icon-smile"></use>
           </svg>
         </div>
            <p>${message}</p>
        </div>
    `;

    this._clear();
    this._parentElement.insertAdjacentHTML("afterbegin", markup);
  }
}
