package squaring.vitrox.paginationlist.Common;

public class SearchHelper {

    private String CurrentSearch="";


    public boolean isNotSameOrEmpty(String text)
    {
        return (text.length()>0&&!text.equals(CurrentSearch));
    }

    public String getCurrentSearch()
    {
        return CurrentSearch;
    }
    public void setCurrentSearch(String currentSearch) {
        CurrentSearch = currentSearch;
    }
}
