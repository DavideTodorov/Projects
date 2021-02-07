import { async } from "regenerator-runtime";
import { API_URL } from "./configuration.js";
import { getJSON } from "./helpers.js";

//Model state
export const state = {
  recipe: {},
};

//Method to load a recipe by given id
export const loadRecipe = async function (id) {
  try {
    const data = await getJSON(`${API_URL}/${id}`);

    const { recipe } = data.data;

    state.recipe = {
      id: recipe.id,
      title: recipe.title,
      publisher: recipe.publisher,
      sourceUrl: recipe.source_url,
      image: recipe.image_url,
      servings: recipe.servings,
      cookingTime: recipe.cooking_time,
      ingredients: recipe.ingredients,
    };

  } catch (err) {
    console.error(err);
  }
};
