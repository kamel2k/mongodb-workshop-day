
angular.module('bookangular').controller('NewAuthorController', function ($scope, $location, locationParser, AuthorResource , BookResource) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.author = $scope.author || {};
    
    $scope.booksList = BookResource.queryAll(function(items){
        $scope.booksSelectionList = $.map(items, function(item) {
            return ( {
                value : item.id,
                text : item.id
            });
        });
    });
    $scope.$watch("booksSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.author.books = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.id = selectedItem.value;
                $scope.author.books.push(collectionItem);
            });
        }
    });


    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/Authors/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        AuthorResource.save($scope.author, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/Authors");
    };
});