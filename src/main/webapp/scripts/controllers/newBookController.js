
angular.module('bookangular').controller('NewBookController', function ($scope, $location, locationParser, BookResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.book = $scope.book || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/Books/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        BookResource.save($scope.book, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/Books");
    };
});