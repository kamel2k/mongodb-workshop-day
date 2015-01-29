angular.module('bookangular').factory('AuthorResource', function($resource){
    var resource = $resource('rest/authors/:AuthorId',{AuthorId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});