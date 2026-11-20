
document.addEventListener("DOMContentLoaded", function () {

    const searchInput = document.getElementById("returnSearch");
    const table = document.getElementById("returnTable");
    const noResults = document.getElementById("returnNoResults");

    if (!searchInput || !table || !noResults) {
        return;
    }

    searchInput.addEventListener("input", function () {

        const searchValue = searchInput.value.toLowerCase().trim();
        const rows = table.querySelectorAll("tbody tr");

        let visibleCount = 0;

        rows.forEach(function (row) {

            if (row.querySelector(".empty-message")) {
                return;
            }

            const rowText = row.textContent.toLowerCase();
            const matches = rowText.includes(searchValue);

            row.style.display = matches ? "" : "none";

            if (matches) {
                visibleCount++;
            }
        });

        noResults.hidden = visibleCount > 0;

    });

});
