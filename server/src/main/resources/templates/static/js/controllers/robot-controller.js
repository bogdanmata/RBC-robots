rbcRobotModule.controller('RobotListController', [ '$scope', 'RobotFactory', 'robots', function($scope, RobotFactory, robots) {
  $scope.robots = robots;
  $scope.deleteRobot = function(id) {
  };
  $scope.errorMessage = '';
} ]);

rbcRobotModule.controller('RobotCreateController', [ '$scope', '$timeout', 'RobotFactory', function($scope, $timeout, RobotFactory) {
  resetForm();
  $scope.submit = function(){
  }

  function resetForm() {
    $scope.robot = {};
    $scope.errorMessage = '';
  }
} ]);

rbcRobotModule.controller('RobotController', [ '$scope', '$window', 'RobotFactory', 'robot', function($scope, $window, RobotFactory, robot) {
  $scope.robot = robot;
  $scope.deleteRobot = function(id) {
  };

  $scope.errorMessage = '';


} ]);
