import { async } from "regenerator-runtime";
import { TIMEOUT_SEC } from "./configuration.js";

//Timeout method to reject promise after some time
const timeout = function (s) {
  return new Promise(function (_, reject) {
    setTimeout(function () {
      reject(new Error(`Request took too long! Timeout after ${s} second`));
    }, s * 1000);
  });
};

//Get JSON data from a given url
export const getJSON = async function (url) {
  try {
    const res = await Promise.race([fetch(url), timeout(TIMEOUT_SEC)]);
    const data = await res.json();

    if (!res.ok) throw new Error(`Error: ${data.message} (${res.status})`);

    return data;
  } catch (err) {
    throw err;
  }
};