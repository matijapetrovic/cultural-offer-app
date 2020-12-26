package cultureapp.common;

public class CategoryTestData {
    public static final String EXISTING_CATEGORY_NAME_FOR_ID_1 = "Category1";
    public static final String EXISTING_CATEGORY_NAME_FOR_ID_2 = "Category2";
    public static final Long EXISTING_CATEGORY_ID = 1L;
    public static final Long EXISTING_CATEGORY_ID_2 = 2L;
    public static final Long EXISTING_CATEGORY_ID_WITHOUT_SUBCATEGORY = 4L;

    public static final String NON_EXISTING_CATEGORY_NAME = "Category5";
    public static final Long NON_EXISTING_CATEGORY_ID = 5L;

    public static final String INVALID_CATEGORY_NAME = "";
    public static final Long INVALID_CATEGORY_ID = -1L;

    public static final String VALID_CATEGORY_NAME = "Non-empty-string";

    public static final Long VALID_CATEGORY_ID = 1L;

    public static final int CATEGORY_PAGE_SIZE = 2;
    public static final int CATEGORY_FIRST_PAGE = 0;
    public static final int CATEGORY_LAST_PAGE = 1;
}
