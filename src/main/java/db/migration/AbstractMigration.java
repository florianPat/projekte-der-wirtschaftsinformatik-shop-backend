package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;

public abstract class AbstractMigration extends BaseJavaMigration {
  @Override
  public Integer getChecksum() {
    return this.getClass().hashCode();
  }
}
