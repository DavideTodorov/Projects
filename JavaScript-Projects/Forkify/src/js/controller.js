import "core-js/stable";
import "regenerator-runtime/runtime";
import * as model from "./model.js";
import recipeView from "./views/recipeView.js";
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
    alert(err);
  }
};

//Add evenet listeners to certain events
const init = function () {
  recipeView.addHandlerRender(controlRecipes);
};
init();
