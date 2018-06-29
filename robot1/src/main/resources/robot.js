var prepareStrategy = Packages.com.imop.tr.robot.strategy.impl.LoadTestPrepareStrategy(robot, 1000);
robot.addRobotStrategy(prepareStrategy);

var testClickStrategy = Packages.com.imop.tr.robot.strategy.impl.LoadTestClickStrategy(robot, 6000, 500, 1000);
robot.addRobotStrategy(testClickStrategy);

var testOperationStrategy = Packages.com.imop.tr.robot.strategy.impl.LoadTestOperationStrategy(robot, 6000, 500, 1000, 10);
robot.addRobotStrategy(testOperationStrategy);

var testRelationStrategy = Packages.com.imop.tr.robot.strategy.impl.RelationTestStrategy(robot, 6000, 5000);
robot.addRobotStrategy(testRelationStrategy);

