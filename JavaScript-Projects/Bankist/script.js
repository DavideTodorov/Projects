"use strict";

//Accounts data
const account1 = {
  owner: "Jonas Schmedtmann",
  movements: [200, 450, -400, 3000, -650, -130, 70, 1300],
  interestRate: 1.2,
  pin: 1111,
};

const account2 = {
  owner: "Jessica Davis",
  movements: [5000, 3400, -150, -790, -3210, -1000, 8500, -30],
  interestRate: 1.5,
  pin: 2222,
};

const account3 = {
  owner: "Steven Thomas Williams",
  movements: [200, -200, 340, -300, -20, 50, 400, -460],
  interestRate: 0.7,
  pin: 3333,
};

const account4 = {
  owner: "Sarah Smith",
  movements: [430, 1000, 700, 50, 90],
  interestRate: 1,
  pin: 4444,
};

const accounts = [account1, account2, account3, account4];

//Elements
const labelWelcome = document.querySelector(".welcome");
const labelDate = document.querySelector(".date");
const labelBalance = document.querySelector(".balance__value");
const labelSumIn = document.querySelector(".summary__value--in");
const labelSumOut = document.querySelector(".summary__value--out");
const labelSumInterest = document.querySelector(".summary__value--interest");
const labelTimer = document.querySelector(".timer");

const containerApp = document.querySelector(".app");
const containerMovements = document.querySelector(".movements");

const btnLogin = document.querySelector(".login__btn");
const btnTransfer = document.querySelector(".form__btn--transfer");
const btnLoan = document.querySelector(".form__btn--loan");
const btnClose = document.querySelector(".form__btn--close");
const btnSort = document.querySelector(".btn--sort");

const inputLoginUsername = document.querySelector(".login__input--user");
const inputLoginPin = document.querySelector(".login__input--pin");
const inputTransferTo = document.querySelector(".form__input--to");
const inputTransferAmount = document.querySelector(".form__input--amount");
const inputLoanAmount = document.querySelector(".form__input--loan-amount");
const inputCloseUsername = document.querySelector(".form__input--user");
const inputClosePin = document.querySelector(".form__input--pin");

//Method to display movements of an account
const displayMovements = function (movements) {
  //Clear the movements conatiner
  containerMovements.innerHTML = "";

  //Add movement rows to movements container
  movements.forEach(function (movement, i) {
    const movementType = movement > 0 ? "deposit" : "withdrawal";

    const rowHTML = `
    <div class="movements__row">
        <div class="movements__type movements__type--${movementType}">${
      i + 1
    } ${movementType}</div>
        <div class="movements__value">${movement}</div>
    </div>
    `;

    containerMovements.insertAdjacentHTML("afterbegin", rowHTML);
  });
};

displayMovements(account1.movements);

//Calculate and display balance
const calcAndDisplayBalance = function (movements) {
  const sum = movements.reduce(function (acc, movement) {
    return acc + movement;
  }, 0);

  labelBalance.textContent = `${sum}\u20ac`;
};

calcAndDisplayBalance(account1.movements);

//Calculate and display summary of movements
const calcAndDisplaySummary = function (movements) {
  //Calculate and display deposits only
  const depositSum = movements
    .filter((mov) => mov > 0)
    .reduce((acc, mov) => acc + mov);

  labelSumIn.textContent = `${depositSum}\u20ac`;

  //Calculate and display withdrawals only
  const withdrawalsSum = movements
    .filter((mov) => mov < 0)
    .reduce((acc, mov) => acc + mov);

  labelSumOut.textContent = `${Math.abs(withdrawalsSum)}\u20ac`;

  //Calculate and display interests gained on deposits
  const interestRateInPercents = 1.2;
  const totalInterestReceived = movements
    .filter((mov) => mov > 0)
    .map((mov) => mov * (interestRateInPercents / 100))
    .filter(interest => interest >= 1)
    .reduce((sum, interest) => sum + interest);

  labelSumInterest.textContent = `${totalInterestReceived}\u20ac`;
};

calcAndDisplaySummary(account1.movements);

//Method to create username from name
const createUsername = function (name) {
  const username = name
    .toLowerCase()
    .split(/\s+/)
    .map((name) => name[0])
    .join("");

  return username;
};

//Create usernames for all accounts
accounts.forEach(function (acc) {
  acc.username = createUsername(acc.owner);
});