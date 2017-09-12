# 后台计算服务代码

本项目工程依赖工程agg-base工程，并作为aggtec工程的子工程

前台及后台公共代码，需放到agg-base工程中，以便于前后台代码共享

目前支持两个数据库：管理库 metaDs ，业务库 busiDs

建议使用spring juit 进行测试


##本工程使用的的基础组件主要有

### SpringJDBC 主要用途是作为SQL执行器使用 

### guava Google Java项目的核心开发库

### apache common 包，作为基础工具使用，目前工程中使用的是版本3

### ehcache作为系统缓存，后台使用的缓存名称是serverCache
加载缓存 @Cacheable(value = "serviceCache", key="#t00_user.id") ，id为输入参数，可使用spel表达式
删除缓存  @CacheEvict 
跟新缓存 @CachePut  不建议使用该参数，因为spring是使用返回值进行缓存更新， 也就是说如果使用本注解的话，必须返回更新后的结果值，而一般情况下，对于更新，我们返回的是更新记录数，作为更新是否成功的判断标志


