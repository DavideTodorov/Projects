import "core-js/stable";
import "regenerator-runtime/runtime";
import { values } from "regenerator-runtime/runtime";

import * as model from "./model.js";
import recipeView from "./views/recipeView.js";
import searchView from "./views/searchView.js";
import resultsView from "./views/resultsView.js";
import paginationView from "./views/paginationView.js";
import bookmarksView from "./views/bookmarksView.js";
import addRecipeView from "./views/addRecipeView.js";
import { MODEL_CLOSE_SECONDS } from "./configuration.js";

// if (module.hot) {
//   module.hot.accept();
// }

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

    //Update UI to mark selected result
    resultsView.update(model.getSearchResultsPage());
    bookmarksView.update(model.state.bookmarks);

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

//Method to handle the pagination of the results
const controlPagination = function (pageToGoTo) {
  console.log(model.state.search.page);
  //Render New search results
  resultsView.render(model.getSearchResultsPage(pageToGoTo));

  //Render New pagination buttons
  paginationView.render(model.state.search);
};

//Method to handle the change of recipe servings
const cotrolServings = function (newServices) {
  //Update the recipe servings
  model.updateServings(newServices);

  //Update the recipe view
  recipeView.update(model.state.recipe);
};

//Method to handle bookmark button click
const controlBookmark = function () {
  if (!model.state.recipe.bookmarked) {
    //Add bookmark
    model.addBookmark(model.state.recipe);
  } else {
    //Remove bookmark
    model.removeBookmark(model.state.recipe.id);
  }

  //Update the recipe view
  recipeView.update(model.state.recipe);

  //Render the bookmarks in the bookmarks view
  bookmarksView.render(model.state.bookmarks);
};

//Method to load bookmarks on page load
const controlLoadBookmark = function () {
  bookmarksView.render(model.state.bookmarks);
};

//Method to add our own recipe
const controllAddRecipe = async function (newRecipe) {
  try {
    //Render loading spinner
    addRecipeView.renderSpinner();

    //Upload the recipe data
    await model.uploadRecipe(newRecipe);

    //Render recipe
    recipeView.render(model.state.recipe);

    //Render success message
    addRecipeView.renderMessage();

    //Close upload recipe window
    setTimeout(function () {
      addRecipeView.toggleUIDisplay();
    }, MODEL_CLOSE_SECONDS * 1000);
  } catch (err) {
    console.error(err);
    addRecipeView.renderError(err.message);
  }
};

//Add evenet listeners to certain events
const init = function () {
  bookmarksView.addRenderHandler(controlLoadBookmark);
  recipeView.addRenderHandler(controlRecipes);
  recipeView.addServingsUpdateHandler(cotrolServings);
  recipeView.addBookmarkHandler(controlBookmark);
  searchView.addSearchHandler(controlSearchResults);
  paginationView.addClickHandler(controlPagination);
  addRecipeView.addUploadHandler(controllAddRecipe);
};
init();
