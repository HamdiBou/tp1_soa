# Data Source Strategy Pattern

## 📋 Overview

This implementation uses the **Strategy Pattern** to allow switching between different data sources (JSON file vs H2 database) **without any changes to the frontend code**.

The frontend simply calls the same API endpoints, and the backend decides which data source to use based on configuration.

## 🎯 How It Works

### Configuration

Edit `application.properties` and set the strategy:

```properties
# Options: JSON, H2, BOTH
app.datasource.strategy=H2
```

### Available Strategies

| Strategy | Description | Data Returned |
|----------|-------------|---------------|
| `JSON` | Use JSON file only | resto1, resto2 (2 restaurants) |
| `H2` | Use H2 database only | Le Gourmet H2, Sushi Master H2, Burger Palace H2 (3 restaurants) |
| `BOTH` | Combine both sources | All 5 restaurants (3 from H2 + 2 from JSON) |

## 🔧 Architecture

```
┌─────────────────┐
│   Frontend      │
│  (Angular App)  │
└────────┬────────┘
         │
         │ GET /restaurants
         │ (always the same endpoint)
         ▼
┌─────────────────────────────────────┐
│      RestaurantController           │
│  (Frontend-agnostic API layer)      │
└────────┬────────────────────────────┘
         │
         ▼
┌─────────────────────────────────────┐
│     RestaurantService                │
│   (Reads app.datasource.strategy)   │
└────┬────────────────────────────┬───┘
     │                            │
     ▼                            ▼
┌──────────────────┐    ┌──────────────────┐
│ JSON Adapter     │    │ H2 Adapter       │
│ (restaurants.json)│    │ (H2 Database)    │
└──────────────────┘    └──────────────────┘
```

## 📂 Data Sources

### JSON File (`restaurants.json`)
```json
[
  { "id": 1, "name": "resto1", "plats": [...] },
  { "id": 2, "name": "resto2", "plats": [...] }
]
```

### H2 Database (loaded from `restaurants-h2.json`)
```json
[
  { "id": 1, "name": "Le Gourmet H2", "plats": [...] },
  { "id": 2, "name": "Sushi Master H2", "plats": [...] },
  { "id": 3, "name": "Burger Palace H2", "plats": [...] }
]
```

## 🚀 Usage Examples

### Example 1: Use H2 Database

**application.properties:**
```properties
app.datasource.strategy=H2
```

**Frontend calls:**
```javascript
// Angular service - no changes needed!
this.http.get('http://localhost:8088/restaurants')
```

**Result:** 3 restaurants from H2 database

### Example 2: Use JSON File

**application.properties:**
```properties
app.datasource.strategy=JSON
```

**Frontend calls:**
```javascript
// Same code - no changes!
this.http.get('http://localhost:8088/restaurants')
```

**Result:** 2 restaurants from JSON file

### Example 3: Use Both Sources

**application.properties:**
```properties
app.datasource.strategy=BOTH
```

**Frontend calls:**
```javascript
// Same code - still no changes!
this.http.get('http://localhost:8088/restaurants')
```

**Result:** 5 restaurants (3 from H2 + 2 from JSON)

## 🔄 Switching Strategies

### Method 1: Restart Application

1. Edit `application.properties`
2. Change `app.datasource.strategy=JSON` to `app.datasource.strategy=H2`
3. Restart Spring Boot
4. Frontend automatically gets data from new source

### Method 2: Using Docker

1. Edit `application.properties`
2. Run:
   ```bash
   docker exec soa_tp_dev bash -c "pkill -9 java"
   docker-compose up -d
   ```

### Method 3: Environment Variables (Future Enhancement)

You could also set this via environment variable:
```yaml
environment:
  - APP_DATASOURCE_STRATEGY=H2
```

## 📊 API Endpoints

### Frontend Endpoints (Strategy-based)

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/restaurants` | GET | Get all restaurants (uses configured strategy) |
| `/restaurants/{id}` | GET | Get restaurant by ID (uses configured strategy) |
| `/restaurants` | POST | Create restaurant (always saves to H2) |
| `/restaurants/{id}` | DELETE | Delete restaurant (always from H2) |

### Testing Endpoints (Optional - for debugging)

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/restaurants/json` | GET | Force use JSON adapter |
| `/restaurants/h2` | GET | Force use H2 adapter |
| `/restaurants/json/{id}` | GET | Get from JSON by ID |
| `/restaurants/h2/{id}` | GET | Get from H2 by ID |

## 💡 Benefits

✅ **Frontend Independence**: Frontend code never changes
✅ **Easy Switching**: Just change configuration property
✅ **Testability**: Can easily test with different data sources
✅ **Flexibility**: Can combine sources with BOTH strategy
✅ **Hexagonal Architecture**: Clear separation of concerns
✅ **No Code Changes**: Switch sources without recompiling

## 🧪 Testing

```bash
# Set strategy to JSON
# Edit application.properties: app.datasource.strategy=JSON
docker-compose restart dev-environment

# Test
curl http://localhost:8088/restaurants
# Returns: resto1, resto2

# Set strategy to H2
# Edit application.properties: app.datasource.strategy=H2
docker-compose restart dev-environment

# Test again (same endpoint!)
curl http://localhost:8088/restaurants
# Returns: Le Gourmet H2, Sushi Master H2, Burger Palace H2
```

## 📝 Implementation Files

- **Strategy Enum**: `DataSourceStrategy.java`
- **Configuration**: `DataSourceConfig.java`
- **Properties**: `application.properties`
- **Service**: `RestaurantService.java` (implements strategy logic)
- **Controller**: `RestaurantController.java` (frontend-agnostic)
- **Adapters**: 
  - `JsonRestaurantRepositoryAdapter.java`
  - `RestaurantRepositoryAdapter.java` (H2)

## 🎯 Key Code

**Service decides which adapter to use:**
```java
public List<Restaurant> getAllRestaurants() {
    DataSourceStrategy strategy = dataSourceConfig.getStrategy();
    
    switch (strategy) {
        case JSON:
            return jsonRepository.findAll();
        case H2:
            return h2Repository.findAll();
        case BOTH:
            List<Restaurant> combined = new ArrayList<>();
            combined.addAll(h2Repository.findAll());
            combined.addAll(jsonRepository.findAll());
            return combined;
        default:
            return h2Repository.findAll();
    }
}
```

**Frontend always calls the same endpoint:**
```typescript
// Angular service - works with ANY strategy!
getAllRestaurants(): Observable<Restaurant[]> {
  return this.http.get<Restaurant[]>('http://localhost:8088/restaurants');
}
```

Perfect separation of concerns! 🚀
