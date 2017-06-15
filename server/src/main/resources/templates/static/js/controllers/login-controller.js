rbcRobotModule.controller('LoginController', [ '$scope', 'LoginFactory', function($scope, LoginFactory) {

  $scope.username = '';
  $scope.password = '';

  $scope.errorMessage = '';

  $scope.submit = function() {
    LoginFactory.login($scope.username, $scope.password).then(function(response) {
      $scope.errorMessage = '';
    }, function(response) {
      $scope.errorMessage = response;
      console.log(msg);
    });
  };

  $scope.reset = function() {
    $scope.username = '';
    $scope.password = '';
  };
} ]);
