USE careernavigator;
SET NAMES utf8mb4;

-- 专业-岗位映射关系（覆盖全部学科门类）

-- 获取专业ID和分类ID的辅助视图已省略，直接使用子查询

-- === 工学 - 计算机类 ===
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 95, 'S', '互联网大厂核心招聘专业'
FROM major m, job_category jc WHERE m.major_code='080901' AND jc.name='互联网大厂';
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 90, 'S', 'AI公司算法岗首选'
FROM major m, job_category jc WHERE m.major_code='080901' AND jc.name='AI公司';
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 88, 'A', '大模型研发核心岗位'
FROM major m, job_category jc WHERE m.major_code='080901' AND jc.name='大模型公司';
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 85, 'A', '外企研发中心主力专业'
FROM major m, job_category jc WHERE m.major_code='080901' AND jc.name='科技外企';
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 75, 'B', '中电科软件开发'
FROM major m, job_category jc WHERE m.major_code='080901' AND jc.name='中电科';

-- 软件工程
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 95, 'S', '互联网开发岗首选'
FROM major m, job_category jc WHERE m.major_code='080902' AND jc.name='互联网大厂';
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 88, 'A', '外企软件开发'
FROM major m, job_category jc WHERE m.major_code='080902' AND jc.name='科技外企';
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 85, 'A', '大模型工程化'
FROM major m, job_category jc WHERE m.major_code='080902' AND jc.name='大模型公司';
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 78, 'B', '银行科技岗'
FROM major m, job_category jc WHERE m.major_code='080902' AND jc.name='银行';

-- 人工智能
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 98, 'S', 'AI公司核心招聘专业'
FROM major m, job_category jc WHERE m.major_code='080917T' AND jc.name='AI公司';
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 95, 'S', '大模型研发首选'
FROM major m, job_category jc WHERE m.major_code='080917T' AND jc.name='大模型公司';
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 90, 'S', '互联网AI算法岗'
FROM major m, job_category jc WHERE m.major_code='080917T' AND jc.name='互联网大厂';
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 85, 'A', '中科院AI研究'
FROM major m, job_category jc WHERE m.major_code='080917T' AND jc.name='中科院系统';
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 82, 'A', '外企AI研发'
FROM major m, job_category jc WHERE m.major_code='080917T' AND jc.name='科技外企';

-- 数据科学
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 92, 'S', '互联网数据分析岗'
FROM major m, job_category jc WHERE m.major_code='080910T' AND jc.name='互联网大厂';
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 85, 'A', '银行数据建模'
FROM major m, job_category jc WHERE m.major_code='080910T' AND jc.name='银行';
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 82, 'A', 'AI公司数据工程'
FROM major m, job_category jc WHERE m.major_code='080910T' AND jc.name='AI公司';

-- 信息安全
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 88, 'A', '网络安全公司'
FROM major m, job_category jc WHERE m.major_code='080904K' AND jc.name='AI公司';
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 80, 'A', '政府机关网络安全'
FROM major m, job_category jc WHERE m.major_code='080904K' AND jc.name='国家公务员';

-- === 工学 - 电子信息类 ===
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 90, 'S', '运营商技术岗核心专业'
FROM major m, job_category jc WHERE m.major_code='080701' AND jc.name='三大运营商';
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 88, 'S', '中电科电子研发首选'
FROM major m, job_category jc WHERE m.major_code='080701' AND jc.name='中电科';
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 82, 'A', '航空电子系统开发'
FROM major m, job_category jc WHERE m.major_code='080701' AND jc.name='中航工业';
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 80, 'A', '半导体芯片设计'
FROM major m, job_category jc WHERE m.major_code='080701' AND jc.name='半导体';

-- 通信工程
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 95, 'S', '三大运营商核心招聘专业'
FROM major m, job_category jc WHERE m.major_code='080703' AND jc.name='三大运营商';
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 88, 'A', '中电科通信研发'
FROM major m, job_category jc WHERE m.major_code='080703' AND jc.name='中电科';
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 80, 'A', '互联网通信技术岗'
FROM major m, job_category jc WHERE m.major_code='080703' AND jc.name='互联网大厂';

-- 集成电路
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 92, 'S', '芯片设计核心专业'
FROM major m, job_category jc WHERE m.major_code='080710T' AND jc.name='半导体';
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 85, 'A', '中电科芯片研发'
FROM major m, job_category jc WHERE m.major_code='080710T' AND jc.name='中电科';

-- === 工学 - 电气类 ===
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 98, 'S', '国家电网第一招聘专业'
FROM major m, job_category jc WHERE m.major_code='080601' AND jc.name='国家电网';
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 80, 'A', '中车电气系统'
FROM major m, job_category jc WHERE m.major_code='080601' AND jc.name='中国中车';
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 78, 'B', '新能源电力电子'
FROM major m, job_category jc WHERE m.major_code='080601' AND jc.name='新能源';

-- === 工学 - 航空航天类 ===
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 98, 'S', '航天科技核心专业'
FROM major m, job_category jc WHERE m.major_code='082002' AND jc.name='航天科技';
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 95, 'S', '航天科工导弹设计'
FROM major m, job_category jc WHERE m.major_code='082002' AND jc.name='航天科工';
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 90, 'S', '中航工业飞机设计'
FROM major m, job_category jc WHERE m.major_code='082002' AND jc.name='中航工业';

-- === 工学 - 机械类 ===
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 88, 'A', '兵器工业机械设计'
FROM major m, job_category jc WHERE m.major_code='080201' AND jc.name='兵器工业';
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 85, 'A', '中车机械研发'
FROM major m, job_category jc WHERE m.major_code='080201' AND jc.name='中国中车';
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 80, 'A', '新能源汽车制造'
FROM major m, job_category jc WHERE m.major_code='080201' AND jc.name='新能源';

-- 车辆工程
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 90, 'S', '汽车企业核心专业'
FROM major m, job_category jc WHERE m.major_code='080207' AND jc.name='新能源';
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 82, 'A', '中车车辆研发'
FROM major m, job_category jc WHERE m.major_code='080207' AND jc.name='中国中车';

-- === 工学 - 自动化类 ===
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 88, 'A', '智能制造自动化'
FROM major m, job_category jc WHERE m.major_code='080801' AND jc.name='新能源';
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 85, 'A', '机器人企业'
FROM major m, job_category jc WHERE m.major_code='080801' AND jc.name='AI公司';

-- 机器人工程
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 90, 'S', '机器人公司核心专业'
FROM major m, job_category jc WHERE m.major_code='080803T' AND jc.name='机器人';
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 82, 'A', 'AI公司具身智能'
FROM major m, job_category jc WHERE m.major_code='080803T' AND jc.name='AI公司';

-- === 经济学 ===
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 90, 'S', '银行核心招聘专业'
FROM major m, job_category jc WHERE m.major_code='020101' AND jc.name='银行';
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 88, 'S', '税务局公务员'
FROM major m, job_category jc WHERE m.major_code='020101' AND jc.name='税务局';
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 80, 'A', '咨询公司经济分析'
FROM major m, job_category jc WHERE m.major_code='020101' AND jc.name='咨询公司';

-- 金融学
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 92, 'S', '银行核心专业'
FROM major m, job_category jc WHERE m.major_code='020301K' AND jc.name='银行';
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 85, 'A', '证券基金'
FROM major m, job_category jc WHERE m.major_code='020301K' AND jc.name='互联网大厂';

-- 会计学
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 88, 'A', '四大会计师事务所'
FROM major m, job_category jc WHERE m.major_code='120203K' AND jc.name='会计师事务所';
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 82, 'A', '银行财务岗'
FROM major m, job_category jc WHERE m.major_code='120203K' AND jc.name='银行';

-- === 法学 ===
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 90, 'S', '公检法系统核心专业'
FROM major m, job_category jc WHERE m.major_code='030101K' AND jc.name='国家公务员';
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 88, 'S', '国考法学类岗位最多'
FROM major m, job_category jc WHERE m.major_code='030101K' AND jc.name='税务局';
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 75, 'B', '四大会计师事务所合规岗'
FROM major m, job_category jc WHERE m.major_code='030101K' AND jc.name='会计师事务所';

-- === 医学 ===
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 98, 'S', '公立医院临床岗位'
FROM major m, job_category jc WHERE m.major_code='100201K' AND jc.name='公立医院';
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 85, 'A', '疾控中心等卫生事业单位'
FROM major m, job_category jc WHERE m.major_code='100201K' AND jc.name='疾控中心';

-- 口腔医学
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 95, 'S', '口腔医院'
FROM major m, job_category jc WHERE m.major_code='100301K' AND jc.name='公立医院';

-- 药学
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 85, 'A', '制药企业'
FROM major m, job_category jc WHERE m.major_code='100701' AND jc.name='AI公司';

-- === 教育学 ===
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 90, 'S', '公立学校教师'
FROM major m, job_category jc WHERE m.major_code='040107' AND jc.name='公立学校';
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 85, 'A', '教育机构'
FROM major m, job_category jc WHERE m.major_code='040107' AND jc.name='公立学校';

-- === 理学 ===
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 85, 'A', '金融量化'
FROM major m, job_category jc WHERE m.major_code='070101' AND jc.name='银行';
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 80, 'A', '互联网算法'
FROM major m, job_category jc WHERE m.major_code='070101' AND jc.name='互联网大厂';

-- 统计学
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 90, 'S', '互联网数据分析'
FROM major m, job_category jc WHERE m.major_code='071201' AND jc.name='互联网大厂';
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 85, 'A', '银行风控建模'
FROM major m, job_category jc WHERE m.major_code='071201' AND jc.name='银行';

-- 物理学
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 82, 'A', '中科院物理研究'
FROM major m, job_category jc WHERE m.major_code='070201' AND jc.name='中科院系统';
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 75, 'B', '半导体物理'
FROM major m, job_category jc WHERE m.major_code='070201' AND jc.name='半导体';

-- === 管理学 ===
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 85, 'A', '互联网产品运营'
FROM major m, job_category jc WHERE m.major_code='120201K' AND jc.name='互联网大厂';
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 80, 'A', '银行管理岗'
FROM major m, job_category jc WHERE m.major_code='120201K' AND jc.name='银行';

-- 电子商务
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 85, 'A', '电商平台运营'
FROM major m, job_category jc WHERE m.major_code='120801' AND jc.name='互联网大厂';

-- === 文学 ===
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 75, 'B', '教育行业'
FROM major m, job_category jc WHERE m.major_code='050101' AND jc.name='公立学校';
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 70, 'B', '公务员文秘'
FROM major m, job_category jc WHERE m.major_code='050101' AND jc.name='国家公务员';

-- 新闻学
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 78, 'B', '互联网新媒体'
FROM major m, job_category jc WHERE m.major_code='050301' AND jc.name='互联网大厂';

-- === 农学 ===
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 80, 'A', '农业科研院所'
FROM major m, job_category jc WHERE m.major_code='090101' AND jc.name='科研院所';
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 70, 'B', '农业公务员'
FROM major m, job_category jc WHERE m.major_code='090101' AND jc.name='国家公务员';

-- 动物医学
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 85, 'A', '宠物医院'
FROM major m, job_category jc WHERE m.major_code='090401' AND jc.name='公立医院';

-- === 艺术学 ===
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 80, 'A', '互联网UI设计'
FROM major m, job_category jc WHERE m.major_code='130502' AND jc.name='互联网大厂';
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 75, 'B', '广告设计'
FROM major m, job_category jc WHERE m.major_code='130502' AND jc.name='AI公司';

-- 数字媒体艺术
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 82, 'A', '互联网视觉设计'
FROM major m, job_category jc WHERE m.major_code='130508' AND jc.name='互联网大厂';

-- 动画
INSERT IGNORE INTO major_job_mapping (major_id, job_category_id, relevance_score, recommendation_level, description)
SELECT m.id, jc.id, 78, 'B', '游戏动画'
FROM major m, job_category jc WHERE m.major_code='130310' AND jc.name='互联网大厂';
