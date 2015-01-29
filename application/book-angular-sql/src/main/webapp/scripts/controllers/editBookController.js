

angular.module('bookangular').controller('EditBookController', function($scope, $routeParams, $location, BookResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.book = new BookResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/Books");
        };
        BookResource.get({BookId:$routeParams.BookId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.book);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.book.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/Books");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/Books");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.book.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});