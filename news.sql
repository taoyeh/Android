USE [master]
GO
/****** Object:  Database [News]    Script Date: 2020/5/5 19:08:15 ******/
CREATE DATABASE [News]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'News', FILENAME = N'D:\Program Files\Microsoft SQL Server\MSSQL14.MSSQLSERVER\MSSQL\DATA\News.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'News_log', FILENAME = N'D:\Program Files\Microsoft SQL Server\MSSQL14.MSSQLSERVER\MSSQL\DATA\News_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
GO
ALTER DATABASE [News] SET COMPATIBILITY_LEVEL = 140
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [News].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [News] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [News] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [News] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [News] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [News] SET ARITHABORT OFF 
GO
ALTER DATABASE [News] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [News] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [News] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [News] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [News] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [News] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [News] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [News] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [News] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [News] SET  DISABLE_BROKER 
GO
ALTER DATABASE [News] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [News] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [News] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [News] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [News] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [News] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [News] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [News] SET RECOVERY FULL 
GO
ALTER DATABASE [News] SET  MULTI_USER 
GO
ALTER DATABASE [News] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [News] SET DB_CHAINING OFF 
GO
ALTER DATABASE [News] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [News] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [News] SET DELAYED_DURABILITY = DISABLED 
GO
EXEC sys.sp_db_vardecimal_storage_format N'News', N'ON'
GO
ALTER DATABASE [News] SET QUERY_STORE = OFF
GO
USE [News]
GO
/****** Object:  Table [dbo].[Category]    Script Date: 2020/5/5 19:08:15 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Category](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[description] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_Category] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[News]    Script Date: 2020/5/5 19:08:15 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[News](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[title] [nvarchar](50) NULL,
	[publish_date] [nvarchar](50) NULL,
	[cid] [int] NULL,
	[front_image] [nvarchar](max) NULL,
	[content] [nvarchar](max) NULL,
 CONSTRAINT [PK_News] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[Category] ON 

INSERT [dbo].[Category] ([id], [description]) VALUES (1, N'全部')
INSERT [dbo].[Category] ([id], [description]) VALUES (2, N'国内')
INSERT [dbo].[Category] ([id], [description]) VALUES (3, N'国外')
INSERT [dbo].[Category] ([id], [description]) VALUES (4, N'军事')
INSERT [dbo].[Category] ([id], [description]) VALUES (5, N'科技')
INSERT [dbo].[Category] ([id], [description]) VALUES (6, N'体育')
SET IDENTITY_INSERT [dbo].[Category] OFF
SET IDENTITY_INSERT [dbo].[News] ON 

INSERT [dbo].[News] ([id], [title], [publish_date], [cid], [front_image], [content]) VALUES (1, N'记录本地生活', N'2020-05-03 ', 3, N't1.jpg', N'最近一阵子罐装食品和冷冻食品的销售量急速上升，因为人们想方设法囤积能够存放很久而不会过期的食物，甚至连冷冻库的销量都增加了。\n但是很多人相信，蔬菜和水果还是吃新鲜的最好，最有营养价值，那么吃罐头水果和蔬菜，或是冷冻水果和蔬菜，对我们健康有损害吗？\n联合国粮农组织（UN Food and Agriculture Organization）高级营养官员哈切姆（Fatima Hachem）说，食物在收成的时候营养价值最高，新鲜的农作物从收割下来就开始流失营养，因为一离开土壤或树枝，也就失去了营养和能量的来源。\n“用来新鲜烹煮的蔬菜如果放久了可能会流失一些营养价值。”\n蔬菜水果采摘下来后就开始利用本身的营养素来维持细胞生命，有些营养素特别容易丧失，例如帮助人体吸收铁，降低胆固醇，抵抗自由基的维生素C，如果暴露在空气中和光线下特别容易流失。\n将新鲜蔬果放进冰箱冷藏能够减缓营养流失的速度，但是每一种蔬菜和水果的营养价值流失的速度不同。\n有关新鲜蔬果、冷冻蔬果和罐头蔬果的营养价值已经有许多的研究，2007年的时候，美国加州大学戴维斯分校（University of California, Davis）的食品科学研究员巴瑞特（Diane Barrett）对多项研究进行检讨发现，菠菜存放在摄氏20度的室温下7天或就流失100%的维生素C，如果放在冰箱里冷藏，流失75%的维生素C，但是红萝卜存放在相同室温下7天后只流失27%的维生素C。\n巴瑞特说，和红萝卜相比，菠菜又嫩又薄，容易流失水分氧化，耐热性也较差。')
INSERT [dbo].[News] ([id], [title], [publish_date], [cid], [front_image], [content]) VALUES (2, N'新冠病毒到底来自哪里？', N'2020-05-04 ', 2, N't2.png', N'法国巴斯德研究所4月28日发布新闻公告称，该所的一项“法国输入性与早期传播病毒的溯源分析”研究显示，法国的新冠肺炎疫情由一种在本地流传的来源未知的病毒毒株所引发。这一研究成果已于近日以预发表的形式在美国生物学论文档案网发布。

　　根据公告，巴斯德研究所对在1月24日至3月24日间采集的97份法国新冠病毒样本进行了基因测序和比对，同时与全球共享流感数据倡议组织发布的338份病毒基因序列进行对比分析，建立了病毒进化树图谱。通过对比研究发现，法国流行的新冠病毒来自于在本土已经流传的某个病毒的进化枝。

　　研究人员通过深入分析后发现，法国本土疫情的病毒分支，与报告的中国和意大利的输入病例都不相同。此项研究牵头人之一、巴斯德研究所RNA病毒进化基因组学负责人艾蒂安·西蒙—洛里埃指出：“现在我们得出结论，关于病毒地理起源的推论并不可靠。”')
INSERT [dbo].[News] ([id], [title], [publish_date], [cid], [front_image], [content]) VALUES (11, N'博大精深', N'2020-05-05', 2, N't3.jpg', N'马克思是马克思主义政党的缔造者和国际共产主义的开创者，是近代以来最伟大的思想家。两个多世纪过去了，人类社会发生了巨大而深刻的变化，但马克思的名字依然在世界各地受到人们的尊敬，马克思的学说依然闪烁着耀眼的真理光芒！

　　习近平总书记强调，马克思主义思想理论博大精深、常学常新。新时代，中国共产党人仍然要学习马克思，学习和实践马克思主义，不断从中汲取科学智慧和理论力量。今天，让我们一起重温马克思的经典话语，感悟真理的魅力。')
INSERT [dbo].[News] ([id], [title], [publish_date], [cid], [front_image], [content]) VALUES (12, N'钟南山连线留学生', N'2020-05-04 ', 2, N't4.jpg', N'据世卫组织最新数据，全球新冠肺炎累计确诊病例超340万例。疫情防控形势依然严峻，我国140多万海外留学生应如何做好自我防护？如何将疫情对学业的影响降至最低？5月4日晚，外交部和国家卫健委联合邀请中国工程院院士钟南山为美国、俄罗斯等疫情高发地区的留学生答疑解惑。连线一个多小时，信息量很大！')
INSERT [dbo].[News] ([id], [title], [publish_date], [cid], [front_image], [content]) VALUES (17, N'name', N'4', 2, N't3.jpg', N'　　4')
SET IDENTITY_INSERT [dbo].[News] OFF
USE [master]
GO
ALTER DATABASE [News] SET  READ_WRITE 
GO
