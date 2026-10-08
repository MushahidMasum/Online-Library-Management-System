function login() {

    let username = document.getElementById("username").value.trim();
    let password = document.getElementById("password").value.trim();

    if (username === "admin" && password === "admin123") {

        window.location.href = "dashboard.html";

    } else {

        document.getElementById("message").innerHTML =
            "Invalid username or password!";

    }
}
function issueBook() {

    let studentId = document.getElementById("studentId").value;
    let bookId = document.getElementById("bookId").value;

    if (studentId === "" || bookId === "") {

        document.getElementById("message").innerHTML =
            "Please enter Student ID and Book ID!";

        return;
    }

    document.getElementById("message").innerHTML =
        "Book issued successfully!";

}
function returnBook() {

    let studentId =
        document.getElementById("returnStudentId").value;

    let bookId =
        document.getElementById("returnBookId").value;

    if (studentId === "" || bookId === "") {

        document.getElementById("message").innerHTML =
            "Please enter Student ID and Book ID!";

        return;
    }

    document.getElementById("message").innerHTML =
        "Book returned successfully!";
}
function addBook() {

    let title = document.getElementById("bookTitle").value.trim();
    let author = document.getElementById("bookAuthor").value.trim();
    let category = document.getElementById("bookCategory").value.trim();
    let quantity = document.getElementById("bookQuantity").value;

    if (title === "" || author === "" ||
        category === "" || quantity === "") {

        document.getElementById("message").innerHTML =
            "Please fill all fields!";

        return;
    }

    document.getElementById("message").innerHTML =
        "Book added successfully!";
}
function addStudent() {

    let name = document.getElementById("studentName").value.trim();
    let email = document.getElementById("studentEmail").value.trim();
    let phone = document.getElementById("studentPhone").value.trim();

    if (name === "" || email === "" || phone === "") {

        document.getElementById("message").innerHTML =
            "Please fill all fields!";

        return;
    }

    document.getElementById("message").innerHTML =
        "Student added successfully!";
}
function searchBook() {

    let keyword = document.getElementById("searchBox").value.trim().toLowerCase();

    let rows = document.querySelectorAll("#bookTable tr");

    for (let row of rows) {

        let title = row.cells[1].innerText.toLowerCase();
        let author = row.cells[2].innerText.toLowerCase();
        let category = row.cells[3].innerText.toLowerCase();

        if (
            title.includes(keyword) ||
            author.includes(keyword) ||
            category.includes(keyword)
        ) {
            row.style.display = "";
        } else {
            row.style.display = "none";
        }
    }
}