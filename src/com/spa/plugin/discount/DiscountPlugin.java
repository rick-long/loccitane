package com.spa.plugin.discount;

/**
 * @author Ivy on 2018-7-17
 */
public class DiscountPlugin {

    //private static final Logger logger = LoggerFactory.getLogger(DiscountPlugin.class);

    //private static final DiscountRuleService discountRuleService = SpringUtil.getBean(DiscountRuleServiceImpl.class);

    /**
     * discount fire process
     *
     * @param discountAdapter 订单适配器
     */
    public static void process(DiscountAdapter discountAdapter) {
        Long shopId = discountAdapter.getShopId();
        Long memberId = discountAdapter.getMemberId();
        for (DiscountItemAdapter itemAdapter : discountAdapter.getItemAdapters()) {
            DiscountItemPlugin.process(itemAdapter, shopId, memberId);
        }

        /*Shop shop = discountAdapter.getShop();
        User member = discountAdapter.getMember();
        //  查找系统所有的discount rule
        List<DiscountRule> discountRuleList = discountRuleService.getList(shop, member);
        if (discountRuleList == null || discountRuleList.size() == 0) {
            logger.debug("No discount rule find for user:{}", member);
            return;
        }

        Stream<DiscountRule> discountRuleStream = discountRuleList.stream();
        for (DiscountItemAdapter itemAdapter : discountAdapter.getItemAdapters()) {
            ProductOption productOption = itemAdapter.getProductOption();
            Product product = productOption.getProduct();
            // 过滤产品或者目录
            List<DiscountRule> availableDiscountRuleList = discountRuleStream.filter(item -> {
                if (item.getProducts().contains(product)) {
                    return true;
                }
                Set<Category> categorySet = item.getCategories();
                for (Category category = product.getCategory(); category.getCategory() != null; category = category.getCategory()) {
                    if (categorySet.contains(category)) {
                        return true;
                    }
                }
                return false;
            }).collect(Collectors.toList());

            // 计算最好的折扣

            BigDecimal maxDiscountValue = new BigDecimal(0);
            DiscountRule maxDiscountRule = null;
            for (DiscountRule discountRule : availableDiscountRuleList) {
                StatefulKnowledgeSession session = KnowLedgeBaseReader.getSession(discountRule.getDiscountTemplate().getContent());
                session.insert(discountRule);
                session.insert(itemAdapter);
                session.fireAllRules();
                session.dispose();

                // 记录最大的discount
                if (maxDiscountValue.compareTo(itemAdapter.getDiscountValue()) < 0) {
                    maxDiscountValue = itemAdapter.getDiscountValue();
                    maxDiscountRule = discountRule;
                }
            }

            // 回填最大的discount和应用的规则
            itemAdapter.setDiscountValue(maxDiscountValue);
            itemAdapter.setDiscountRule(maxDiscountRule);
        }*/
    }

    /*public static KieSession getKieSession(String drlFile) {
        *//*KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        Resource resource = ResourceFactory.newClassPathResource("com/spa/drl/Discount.drl");
        System.out.println("resource:" + resource);
        kbuilder.add(resource, ResourceType.DRL);
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kContainer = kieServices.getKieClasspathContainer();
        KieSession kSession = kContainer.newKieSession();
        return kSession;*//*
        
        // load up the knowledge base
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession kSession = kContainer.newKieSession("ksession-rules");

        KieScanner kScanner = ks.newKieScanner( kContainer );
        kScanner.scanNow();
        return kSession;
    }*/
}
