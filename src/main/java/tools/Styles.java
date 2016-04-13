package tools;

import java.util.List;

public class Styles
{
  private List<String> list;

  public List<String> getList()
  {
    return this.list;
  }

  public void setList(List<String> list) {
    this.list = list;
  }

  public String getRandomStyle() {
    return (String)this.list.get((int)(0.0D + Math.random() * 20.0D));
  }
}