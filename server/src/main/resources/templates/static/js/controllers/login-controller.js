rbcRobotModule.controller('LoginController', [ '$rootScope', '$scope', '$location', 'LoginFactory', function($rootScope, $scope, $location, LoginFactory) {

  $scope.username = '';
  $scope.password = '';

  $scope.errorMessage = '';

  $scope.submit = function() {
    LoginFactory.login($scope.username, $scope.password).then(function(loggedUser) {
      $scope.errorMessage = '';
      $rootScope.currentUser = loggedUser;
      $location.path('/');
    }, function(error) {
      $scope.errorMessage = error.errorMessage;
      console.log(msg);
    });
  };

  $scope.reset = function() {
    $scope.username = '';
    $scope.password = '';
  };
} ]);
