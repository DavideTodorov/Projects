import "core-js/stable";
import "regenerator-runtime/runtime";
import { values } from "regenerator-runtime/runtime";

import * as model from "./model.js";
import recipeView from "./views/recipeView.js";
import searchView from "./views/searchView.js";
import resultsView from "./views/resultsView.js";
import paginationView from "./views/paginationView.js";

if (module.hot) {
  module.hot.accept();
}

//=========================================================
//Elements
const recipeContainer = document.querySelector(".recipe");

//Search API
// https://forkify-api.herokuapp.com/v2

//Async function to load recipe
const controlRecipes = async function () {
  try {
    //Get current hash from url
    const id = window.location.hash.slice(1);
    if (!id) return;
    recipeView.renderSpinner(recipeContainer);

    //Load recipe
    await model.loadRecipe(id);

    //Render recipe
    recipeView.render(model.state.recipe);
  } catch (err) {
    recipeView.renderError();
  }
};

//Method to control searching for recepes
const controlSearchResults = async function () {
  try {
    resultsView.renderSpinner();
    //Get search query
    const query = searchView.getQuery();
    if (!query) return;

    //Load search results
    await model.loadAndSearchResults(query);

    //Render search results
    resultsView.render(model.getSearchResultsPage());

    //Render pagination buttons
    paginationView.render(model.state.search);
  } catch (err) {
    console.error(err);
  }
};

const controlPagination = function (pageToGoTo) {
  console.log(model.state.search.page);
  //Render New search results
  resultsView.render(model.getSearchResultsPage(pageToGoTo));

  //Render New pagination buttons
  paginationView.render(model.state.search);
};

//Add evenet listeners to certain events
const init = function () {
  recipeView.addRenderHandler(controlRecipes);
  searchView.addSearchHandler(controlSearchResults);
  paginationView.addClickHandler(controlPagination);
};
init();
