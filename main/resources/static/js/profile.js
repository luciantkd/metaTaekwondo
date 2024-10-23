let formChanged = false;
let isSubmitting = false;

function trackChanges() {
    formChanged = true;
}

// Function to cancel changes and redirect to the profile page
function cancelChanges() {
    window.location.href = '/profile';
}

document.getElementById('profileForm').addEventListener('submit', function (event) {
    event.preventDefault();
    $('#confirmationModal').modal('show');
});

$(document).ready(function () {
    document.getElementById('confirmButton').addEventListener('click', function () {
        isSubmitting = true;
        $('#confirmationModal').modal('hide');

        const formAction = $('#profileForm').attr('action'); // Get the form action URL
        console.log('Form action URL:', formAction);

        // Send the form data via an AJAX POST request
        $.ajax({
            type: 'POST',
            url: formAction,
            data: $('#profileForm').serialize(),
            success: function (response) {
                if (response.success) {
                    // Update the bio field directly with the JSON data
                    $('#bio').val(response.bio);
                    $('#successModal').modal('show');
                    formChanged = false;

                    // Redirect to the profile page after closing the success modal
                    $('#successModal').on('hidden.bs.modal', function () {
                        window.location.href = '/profile';
                    });
                } else {
                    alert('Error saving changes');
                }
                isSubmitting = false;
            },
            error: function () {
                alert('Error saving changes');
                isSubmitting = false;
            }
        });
    });

    // Event listener for the "OK" button in the success modal
    $('#successModal').on('shown.bs.modal', function () {
        $('#successModal button').click(function () {
            window.location.href = '/profile';
        });
    });

});

// Function to confirm navigation away from the page if there are unsaved changes
function confirmNavigation(event) {
    if (formChanged && !isSubmitting) {
        event.preventDefault();
        $('#leaveSiteModal').modal('show');
    }
}

document.getElementById('statistics-link').addEventListener('click', confirmNavigation);
document.querySelector('.logo a').addEventListener('click', confirmNavigation);

document.querySelector('#confirmationModal .btn-secondary').addEventListener('click', function () {
    $('#confirmationModal').modal('hide');
});

document.getElementById('leaveButton').addEventListener('click', function () {
    window.location.href = '/home';
});
