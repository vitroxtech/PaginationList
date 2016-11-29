package squaring.vitrox.paginationlist.Common;

public class PaginationHelper {

    private int TotalPages=-1;

    private int CurrentPage=0;

    private int PageSize=Config.PAGE_SIZE;

    private int TotalItems;


    public PaginationHelper(int totalItems) {
        CurrentPage=1;
        TotalItems=totalItems;
        TotalPages= (int) Math.ceil(totalItems/PageSize);
    }

    public PaginationHelper(){
        //base constructor for Dagger
    }
    public int getCurrentPage() {
            return CurrentPage;
    }

    public int getNextPage()
    {
        if(!isLastPage()) {
            CurrentPage++;
        }
        return CurrentPage;
    }
    public int getPageSize(){
        return PageSize;
    }
    public void setCurrentPage(int currentPage) {
        CurrentPage = currentPage;
    }

    public boolean isLastPage()
    {
       return  (CurrentPage == TotalPages);
    }

}
