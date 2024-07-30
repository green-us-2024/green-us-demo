package androidx.databinding;

public class DataBinderMapperImpl extends MergedDataBinderMapper {
  DataBinderMapperImpl() {
    addMapper(new kr.ac.kpu.green_us.DataBinderMapperImpl());
  }
}
