

angular.module('bookangular').controller('EditAuthorController', function($scope, $routeParams, $location, AuthorResource , BookResource) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.author = new AuthorResource(self.original);
            BookResource.queryAll(function(items) {
                $scope.booksSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        id : item.id
                    };
                    var labelObject = {
                        value : item.id,
                        text : item.id
                    };
                    if($scope.author.books){
                        $.each($scope.author.books, function(idx, element) {
                            if(item.id == element.id) {
                                $scope.booksSelection.push(labelObject);
                                $scope.author.books.push(wrappedObject);
                            }
                        });
                        self.original.books = $scope.author.books;
                    }
                    return labelObject;
                });
            });
        };
        var errorCallback = function() {
            $location.path("/Authors");
        };
        AuthorResource.get({AuthorId:$routeParams.AuthorId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.author);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.author.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/Authors");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/Authors");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.author.$remove(successCallback, errorCallback);
    };
    
    $scope.booksSelection = $scope.booksSelection || [];
    $scope.$watch("booksSelection", function(selection) {
        if (typeof selection != 'undefined' && $scope.author) {
            $scope.author.books = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.id = selectedItem.value;
                $scope.author.books.push(collectionItem);
            });
        }
    });
    
    $scope.get();
});