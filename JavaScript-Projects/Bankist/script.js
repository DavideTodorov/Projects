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

//
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

//==========================================
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

//==============================
//Calculate and display balance
const calcAndDisplayBalance = function (currAccount) {
  const sum = currAccount.movements.reduce(function (acc, movement) {
    return acc + movement;
  }, 0);

  labelBalance.textContent = `${sum}\u20ac`;
  currAccount.balance = sum;
};

//===========================================
//Calculate and display summary of movements
const calcAndDisplaySummary = function (currAcc) {
  //Calculate and display deposits only
  let depositSum = 0;
  try {
    depositSum = currAcc.movements
      .filter((mov) => mov > 0)
      .reduce((acc, mov) => acc + mov);
  } catch (e) {}

  labelSumIn.textContent = `${depositSum}\u20ac`;

  //Calculate and display withdrawals only
  let withdrawalsSum = 0;
  try {
    withdrawalsSum = currAcc.movements
      .filter((mov) => mov < 0)
      .reduce((acc, mov) => acc + mov);
  } catch (e) {}

  labelSumOut.textContent = `${Math.abs(withdrawalsSum)}\u20ac`;

  //Calculate and display interests gained on deposits
  const interestRateInPercents = currAcc.interestRate;
  const totalInterestReceived = currAcc.movements
    .filter((mov) => mov > 0)
    .map((mov) => mov * (interestRateInPercents / 100))
    .filter((interest) => interest >= 1)
    .reduce((sum, interest) => sum + interest);

  labelSumInterest.textContent = `${totalInterestReceived}\u20ac`;
};

//==============================
//Method to update the whole UI
const updateUI = function (currAcc) {
  //Display movements
  displayMovements(currAcc.movements);

  //Calculate and display balance
  calcAndDisplayBalance(currAcc);

  //Display summary
  calcAndDisplaySummary(currAcc);
};

//====================================
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

//============
//Login logic
let currAccount;

//Event handler for the "login" button
btnLogin.addEventListener("click", function (e) {
  e.preventDefault();

  //Get user inputs
  const inputUser = inputLoginUsername.value;
  const inputPin = Number(inputLoginPin.value);

  //Get account based on user inputs
  currAccount = accounts.find((acc) => acc.username === inputUser);

  //Check if password is correct. If correct then log in
  if (currAccount?.pin === inputPin) {
    //Show message
    labelWelcome.textContent = `Welcome back, ${
      currAccount.owner.split(/\s+/)[0]
    }`;

    //Remove user accounts information from starting screen
    document.querySelector(".userAccountsData").style.display = "none";

    //Display UI
    containerApp.style.opacity = 100;

    //Update UI
    updateUI(currAccount);
  }

  //Clear input fields
  inputLoginUsername.value = "";
  inputLoginPin.value = "";
  inputLoginPin.blur();
});

//=====================
//Transfer money logic
//Transfer button event listener
btnTransfer.addEventListener("click", function (e) {
  e.preventDefault();

  const accountTransferTo = accounts.find(
    (acc) => acc.username === inputTransferTo.value
  );
  const transferAmount = Number(inputTransferAmount.value);

  inputTransferTo.value = inputTransferAmount.value = "";

  if (
    transferAmount > 0 &&
    transferAmount <= currAccount.balance &&
    accountTransferTo &&
    accountTransferTo.username !== currAccount.username
  ) {
    //The transfer logic
    currAccount.movements.push(-transferAmount);
    accountTransferTo.movements.push(transferAmount);

    //Update UI
    updateUI(currAccount);
  }
});

//====================
//Close account logic
btnClose.addEventListener("click", function (e) {
  e.preventDefault();

  const closeUsername = inputCloseUsername.value;
  const closePin = Number(inputClosePin.value);

  if (closeUsername === currAccount.username && closePin === currAccount.pin) {
    //Find index of account to be closed
    const index = accounts.findIndex((acc) => acc.username === closeUsername);
    console.log(index);
    //Remove account from the array
    accounts.splice(index, 1);

    //Update UI
    containerApp.style.opacity = 0;
    document.querySelector(".userAccountsData").style.display = "block";
    document.getElementById(
      `userAccountsData.${closeUsername}`
    ).style.visibility = "hidden";

    inputCloseUsername.value = inputClosePin.value = "";

    labelWelcome.textContent = "Log in to get started";
  }
});

//===================
//Request loan logic
btnLoan.addEventListener("click", function (e) {
  e.preventDefault();

  const loanAmount = Number(inputLoanAmount.value);

  if (
    loanAmount > 0 &&
    currAccount.movements.some((mov) => mov >= 0.1 * loanAmount)
  ) {
    //Add the loan to the movements array
    currAccount.movements.push(loanAmount);

    //Update UI
    updateUI(currAccount);
  }
  
  inputLoanAmount.value = "";
});
