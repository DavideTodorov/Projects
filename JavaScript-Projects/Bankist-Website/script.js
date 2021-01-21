"use strict";

//Elements
const modal = document.querySelector(".modal");
const overlay = document.querySelector(".overlay");
const btnCloseModal = document.querySelector(".btn--close-modal");
const btnsOpenModal = document.querySelectorAll(".btn--show-modal");
const btnLearnMore = document.querySelector(".btn--scroll-to");
const section1 = document.getElementById("section--1");
const tabsContainer = document.querySelector(".operations__tab-container");
const tabsContents = document.querySelectorAll(".operations__content");
const navigation = document.querySelector(".nav");
const logo = document.querySelector(".nav__logo");
const header = document.querySelector(".header");

//////////////////////////////////////////////////////////
// Modal window

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

//////////////////////////////////////////////////////////
//Scroll smoothly when press "Learn more" button
btnLearnMore.addEventListener("click", function (e) {
  section1.scrollIntoView({ behavior: "smooth" });
});

//////////////////////////////////////////////////////////
//Page navigation smooth scrolling using event delegation
document.querySelector(".nav__links").addEventListener("click", function (e) {
  e.preventDefault();
  const target = e.target;

  //Matching strategy
  if (target.classList.contains("nav__link")) {
    const id = target.getAttribute("href");
    document.querySelector(id).scrollIntoView({ behavior: "smooth" });
  }
});

//////////////////////////////////////////////////////////
//Switch components when press a given tab
tabsContainer.addEventListener("click", function (e) {
  //Clicked tab
  const target = e.target.closest(".operations__tab");

  //Guard clause
  if (!target) return;

  //Remove active status from all tabs
  document
    .querySelectorAll(".operations__tab")
    .forEach((t) => t.classList.remove("operations__tab--active"));

  //Add active status to the pressed tab
  target.classList.add("operations__tab--active");

  //Remove active status from all tabs contents
  tabsContents.forEach((c) =>
    c.classList.remove("operations__content--active")
  );

  //Add active status to the content related to the pressed tab
  const data = target.dataset.tab;
  document
    .querySelector(`.operations__content--${data}`)
    .classList.add("operations__content--active");
});

//////////////////////////////////////////////////////////
//Menu fade animation when a tab is hovered
const hoverHandler = function (e) {
  const target = e.target;

  if (target.classList.contains("nav__link")) {
    document.querySelectorAll(".nav__link").forEach((el) => {
      if (el !== target) el.style.opacity = this;
    });
    logo.style.opacity = this;
    console.log("Hover");
  }
};

navigation.addEventListener("mouseover", hoverHandler.bind(0.5));
navigation.addEventListener("mouseout", hoverHandler.bind(1));

//////////////////////////////////////////////////////////
//Make the navigation "sticky"
const navigationHeight = navigation.getBoundingClientRect().height;

//Observer callback method
const stickyNavCallback = function (entries) {
  const [entry] = entries;

  if (!entry.isIntersecting) {
    navigation.classList.add("sticky");
  } else {
    navigation.classList.remove("sticky");
  }
};

//Observer
const stickyNavObserver = new IntersectionObserver(stickyNavCallback, {
  root: null,
  threshold: 0,
  rootMargin: `-${navigationHeight}px`,
});
stickyNavObserver.observe(header);
