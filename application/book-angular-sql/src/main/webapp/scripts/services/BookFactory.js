angular.module('bookangular').factory('BookResource', function($resource){
    var resource = $resource('rest/books/:BookId',{BookId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});