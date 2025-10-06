# Technological Revolution

这是一个为 Minecraft 1.20.1 设计的 Forge 模组，名为 "Technological Revolution"。该模组旨在为游戏添加科技元素，包括新的能源生成方式（太阳能、风能、水能、核能）、机器（矿石粉碎机、高级熔炉）、能源传输系统（导线）、新的矿石（铀、锂、钛）以及相关的工具和盔甲。

## 核心特性

- **能源系统**: 实现了一个基于FE（Forge Energy）的能量系统，用于机器和设备之间的能量传输与存储。
- **能源生成**: 添加了多种能源生成方块，如太阳能板、风力发电机、水力发电机和核反应堆。
- **机器**: 添加了用于处理资源的机器，如矿石粉碎机和高级熔炉。
- **能源传输**: 通过不同等级的导线（铜线、金线）实现能量传输。
- **新资源**: 添加了铀、锂、钛三种新的矿石及其锭。
- **能源工具与盔甲**: 添加了使用能量的工具（如能量镐）和盔甲（能量头盔、胸甲、护腿、靴子）。

## 技术栈

- **Minecraft 版本**: 1.20.1
- **模组加载器**: Forge (版本 47.3.27)
- **语言**: Java 17
- **构建工具**: Gradle

## 项目结构

```
Technological-Revolution/
├── build.gradle                 # Gradle 构建脚本
├── gradle.properties           # Gradle 属性配置
├── src/
│   ├── main/
│   │   ├── java/               # Java 源代码
│   │   │   └── com/yhj2014/technological_revolution/
│   │   │       ├── TechnologicalRevolutionMod.java  # 主类
│   │   │       ├── block/       # 方块相关代码
│   │   │       ├── block/entity/ # 方块实体相关代码
│   │   │       ├── item/        # 物品相关代码
│   │   │       ├── container/   # 容器（GUI后端）相关代码
│   │   │       ├── client/screen/ # 屏幕（GUI前端）相关代码
│   │   │       ├── energy/      # 能量系统相关代码
│   │   │       ├── advancement/ # 进度系统相关代码
│   │   │       ├── recipe/      # 配方条件相关代码
│   │   │       ├── research/    # 研究系统相关代码（预留）
│   │   │       ├── world/       # 世界生成相关代码
│   │   │       └── Config.java  # 配置文件
│   │   └── resources/          # 资源文件
│   │       ├── assets/technological_revolution/ # 游戏资源（语言、纹理）
│   │       ├── data/technological_revolution/   # 数据文件（配方、进度、世界生成）
│   │       └── META-INF/mods.toml # 模组元数据
│   └── test/                   # 测试代码（目前为空）
└── ...
```

## 构建与运行

### 环境准备

1. 确保已安装 Java 17 JDK。
2. 确保已安装 Minecraft 1.20.1 和对应的 Forge 47.3.27。

### 构建命令

在项目根目录下执行以下命令：

- **生成 Eclipse 运行配置**:
  ```bash
  ./gradlew genEclipseRuns
  ```

- **生成 IntelliJ IDEA 运行配置**:
  ```bash
  ./gradlew genIntellijRuns
  ```

- **构建模组 JAR 文件**:
  ```bash
  ./gradlew build
  ```

- **刷新依赖**:
  ```bash
  ./gradlew --refresh-dependencies
  ```

- **清理构建**:
  ```bash
  ./gradlew clean
  ```

### 运行模组

1. 使用上述命令生成对应 IDE 的运行配置。
2. 在 IDE 中导入项目。
3. 运行 `client` 配置以启动 Minecraft 客户端进行测试。
4. 运行 `server` 配置以启动 Minecraft 服务器进行测试。

## 开发约定

- **代码风格**: 遵循 Java 标准命名和代码风格。
- **包结构**: 按功能模块组织代码，如 `block`, `item`, `block/entity`, `container`, `client/screen` 等。
- **注册**: 使用 Forge 的 `DeferredRegister` 系统来注册方块、物品、方块实体、菜单类型等。
- **能量系统**: 使用 Forge Energy (FE) API 进行能量处理。
- **GUI**: 使用 `AbstractContainerMenu` 和 `AbstractContainerScreen` 来实现容器和屏幕。
- **进度**: 使用自定义的 `ModAdvancementTrigger` 来实现模组特定的进度触发。
- **配方条件**: 使用 `ICondition` 和 `CraftingHelper.register` 来注册自定义配方条件。
- **世界生成**: 通过 `ModWorldGen` 和 `ModOreGeneration` 类来管理世界生成逻辑。

## 重要类和文件

- `TechnologicalRevolutionMod.java`: 模组的主类，负责初始化和注册。
- `AbstractMachineBlock.java` & `AbstractMachineBlockEntity.java`: 机器方块和方块实体的基类，提供了通用的能量存储和处理逻辑。
- `WireBlock.java` & `WireBlockEntity.java`: 导线方块和方块实体，用于能量传输。
- `ModEnergyStorage.java`: 自定义的能量存储实现。
- `ModAdvancements.java` & `ModAdvancementTrigger.java`: 进度系统相关类。
- `ResearchManager.java`: 研究系统管理器（预留）。
- `ResearchCondition.java`: 自定义配方条件。
- `ModEvents.java`: 全局事件监听器（预留）。
- `src/main/resources/data/technological_revolution/recipes/`: 存放所有模组配方的 JSON 文件。
- `src/main/resources/data/technological_revolution/advancements/`: 存放所有模组进度的 JSON 文件。
- `src/main/resources/assets/technological_revolution/lang/`: 存放语言文件。
- `src/main/resources/assets/technological_revolution/textures/gui/`: 存放 GUI 纹理文件。

## 贡献

欢迎任何形式的贡献！请先 fork 项目，然后创建你的功能分支，最后提交 pull request。

## 许可证

本项目采用 [LGPL 2.1] 许可证 - 请查看 [LICENSE](LICENSE) 文件了解详细信息。