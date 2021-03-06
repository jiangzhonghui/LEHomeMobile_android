package my.home.model.entities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import my.home.model.datasource.DaoSession;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table MSG_HISTORY_ITEM.
 */
public class MsgHistoryItemDao extends AbstractDao<MsgHistoryItem, Long> {

    public static final String TABLENAME = "MSG_HISTORY_ITEM";

    /**
     * Properties of entity MsgHistoryItem.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property From = new Property(1, String.class, "from", false, "FROM");
        public final static Property Msg = new Property(2, String.class, "msg", false, "MSG");
    }

    ;


    public MsgHistoryItemDao(DaoConfig config) {
        super(config);
    }

    public MsgHistoryItemDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /**
     * Creates the underlying database table.
     */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "'MSG_HISTORY_ITEM' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'FROM' TEXT NOT NULL ," + // 1: from
                "'MSG' TEXT NOT NULL );"); // 2: msg
    }

    /**
     * Drops the underlying database table.
     */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'MSG_HISTORY_ITEM'";
        db.execSQL(sql);
    }

    /**
     * @inheritdoc
     */
    @Override
    protected void bindValues(SQLiteStatement stmt, MsgHistoryItem entity) {
        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getFrom());
        stmt.bindString(3, entity.getMsg());
    }

    /**
     * @inheritdoc
     */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    /**
     * @inheritdoc
     */
    @Override
    public MsgHistoryItem readEntity(Cursor cursor, int offset) {
        MsgHistoryItem entity = new MsgHistoryItem( //
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
                cursor.getString(offset + 1), // from
                cursor.getString(offset + 2) // msg
        );
        return entity;
    }

    /**
     * @inheritdoc
     */
    @Override
    public void readEntity(Cursor cursor, MsgHistoryItem entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setFrom(cursor.getString(offset + 1));
        entity.setMsg(cursor.getString(offset + 2));
    }

    /**
     * @inheritdoc
     */
    @Override
    protected Long updateKeyAfterInsert(MsgHistoryItem entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    /**
     * @inheritdoc
     */
    @Override
    public Long getKey(MsgHistoryItem entity) {
        if (entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /**
     * @inheritdoc
     */
    @Override
    protected boolean isEntityUpdateable() {
        return true;
    }

}
