
document.addEventListener("DOMContentLoaded", function () {

    const searchInput = document.getElementById("employeeSearch");
    const table = document.getElementById("employeeTable");
    const noResults = document.getElementById("employeeNoResults");

    if (!searchInput || !table || !noResults) {
        return;
    }

    searchInput.addEventListener("input", function () {

        const searchText = searchInput.value.toLowerCase().trim();
        const rows = table.querySelectorAll("tbody tr");

        let visibleCount = 0;

        rows.forEach(function (row) {

            if (row.querySelector(".empty-message")) {
                return;
            }

            const rowText = row.textContent.toLowerCase();
            const matches = rowText.includes(searchText);

            row.style.display = matches ? "" : "none";

            if (matches) {
                visibleCount++;
            }
        });

        noResults.hidden = visibleCount > 0;
    });

});
