import View from "./view.js";
import icons from "url:../../img/icons.svg";

class ResultView extends View {
  _parentElement = document.querySelector(".results");
  _errorMessage = "No recipes were found for your search. Please try again!";
  _defaultMessage = "";

  _generateMarkup() {
    return this._data.map((r) => {
      return `
          <li class="preview">
              <a class="preview__link" href="#${r.id}">
                <figure class="preview__fig">
                  <img src="${r.image}" alt="${r.title}" />
                </figure>
                <div class="preview__data">
                  <h4 class="preview__title">${r.title}</h4>
                  <p class="preview__publisher">${r.publisher}</p>
                </div>
              </a>
          </li>
        `;
    });
  }
}

export default new ResultView();
