"use strict";

///////////////////////////////////////
// Modal window
//Elements
const modal = document.querySelector(".modal");
const overlay = document.querySelector(".overlay");
const btnCloseModal = document.querySelector(".btn--close-modal");
const btnsOpenModal = document.querySelectorAll(".btn--show-modal");

//Functionality
const openModal = function (e) {
  e.preventDefault();
  modal.classList.remove("hidden");
  overlay.classList.remove("hidden");
};

const closeModal = function () {
  modal.classList.add("hidden");
  overlay.classList.add("hidden");
};

//Buttons event listeners
btnsOpenModal.forEach((btn) => btn.addEventListener("click", openModal));
btnCloseModal.addEventListener("click", closeModal);
overlay.addEventListener("click", closeModal);

//Close when "ESC" is pressed
document.addEventListener("keydown", function (e) {
  if (e.key === "Escape" && !modal.classList.contains("hidden")) {
    closeModal();
  }
});

/////////////////////////////////////////////////
//Scroll smoothly when press "Learn more" button

const btnLearnMore = document.querySelector(".btn--scroll-to");
const section1 = document.getElementById("section--1");

btnLearnMore.addEventListener("click", function (e) {
  section1.scrollIntoView({ behavior: "smooth" });
});
