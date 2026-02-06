# 国际化功能单元测试

## 测试概述

本测试套件用于验证项目的国际化(i18n)功能，包括：
- 从请求头读取语言标识
- 根据语言标识返回对应语言的文本消息
- 中英文消息的正确性验证

## 测试文件结构

```
src/test/java/com/refund/i18n/
├── HeaderLocaleResolverTest.java   # HeaderLocaleResolver 单元测试
├── MessageUtilsTest.java           # MessageUtils 单元测试
├── I18nIntegrationTest.java        # 集成测试
└── I18nTestSuite.java              # 测试套件
```

## 测试类说明

### 1. HeaderLocaleResolverTest
测试 `HeaderLocaleResolver` 从请求头读取语言标识并解析为 `Locale` 的功能。

**测试用例：**
- 中文语言标识 (zh-CN, zh_CN)
- 英文语言标识 (en, en-US, en-GB)
- 空/未设置 Accept-Language
- 未知语言标识
- 不区分大小写
- 多语言标识

### 2. MessageUtilsTest
测试 `MessageUtils` 获取国际化消息的功能。

**测试用例：**
- 中文消息获取
- 英文消息获取
- 带参数的消息
- 用户管理消息
- 角色管理消息
- 部门管理消息
- 权限错误消息
- 认证失败消息

### 3. I18nIntegrationTest
完整的集成测试，验证从请求到响应的完整国际化流程。

**测试用例：**
- 中文请求获取中文消息
- 英文请求获取英文消息
- AjaxResult 响应的中英文
- 语言切换功能
- 各模块消息的中英文验证

## 运行测试

### 在IDE中运行
1. 打开任意测试类
2. 右键点击类名或测试方法
3. 选择 "Run" 或 "Debug"

### 使用Maven命令运行

```bash
# 运行所有国际化测试
mvn test -Dtest=com.refund.i18n.*

# 运行单个测试类
mvn test -Dtest=HeaderLocaleResolverTest

# 运行所有测试
mvn test

# 运行测试并生成报告
mvn test -Dtest=com.refund.i18n.* -Dsurefire.reportFormat=html
```

## 测试覆盖的消息类型

### 通用消息
- common.operation.success (操作成功 / Operation successful)
- common.operation.failed (操作失败 / Operation failed)
- common.query.success (查询成功 / Query successful)

### 异常消息
- exception.no_permission (没有权限 / No permission)
- exception.unauthorized_access (认证失败 / authentication failed)
- exception.demo_mode (演示模式 / Demo mode)

### 用户管理消息
- user.delete.current (当前用户不能删除)
- user.not.allow.admin (不允许操作超级管理员用户)
- user.password.failed.old_password (旧密码错误)

### 角色管理消息
- role.not.allow.admin (不允许操作超级管理员角色)
- role.has.assigned (已分配,不能删除)

### 部门管理消息
- dept.has.children (存在下级部门)
- dept.has.users (部门存在用户)
- dept.has.disabled_children (包含未停用的子部门)

## 预期结果

所有测试应该通过，验证以下功能：
1. ✅ 请求头 Accept-Language: zh-CN 返回中文消息
2. ✅ 请求头 Accept-Language: en 返回英文消息
3. ✅ 不指定语言时默认返回中文消息
4. ✅ 带参数的消息正确替换参数
5. ✅ 所有模块的消息都能正确国际化

## 故障排查

### 测试失败的可能原因：

1. **资源文件未找到**
   - 检查 messages_zh.properties 和 messages_en.properties 是否存在
   - 确认文件位置：RefundO-admin/src/main/resources/i18n/

2. **消息键不匹配**
   - 检查资源文件中的键名是否与代码中使用的一致
   - 确保中英文资源文件包含相同的键

3. **编码问题**
   - 确认资源文件使用 UTF-8 编码
   - 检查 properties 文件中的中文字符是否正确显示

4. **Spring配置问题**
   - 确认 I18nConfig 配置正确
   - 检查 MessageSource Bean 是否正确加载

## 扩展测试

要添加新的测试用例：

1. 在对应的测试类中添加新的测试方法
2. 使用 `@Test` 和 `@DisplayName` 注解
3. 使用 `assertEquals` 或其他断言方法验证结果

示例：
```java
@Test
@DisplayName("测试新的国际化消息")
public void testNewMessage()
{
    LocaleContextHolder.setLocale(Locale.ENGLISH);
    String message = MessageUtils.message("new.message.key");
    assertEquals("Expected message", message);
}
```

## 版本历史

- v1.0.0 - 初始版本，包含基础国际化测试
- 支持中文(zh-CN)和英文(en)两种语言
