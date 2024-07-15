namespace ConsoleApp
{
    using System;
    using System.Collections.Generic;
    using System.Text;
    using System.IO;
    using System.Linq;
    using System.Threading.Tasks;

    public class DataReader
    {
        IEnumerable<ImportedObject> ImportedObjects;

        public void ImportAndPrintData(string fileToImport, bool printData = true)
        {
            ImportedObjects = new List<ImportedObject>();

            using (var streamReader = new StreamReader(fileToImport))
            {
                while (!streamReader.EndOfStream)
                {
                    var line = streamReader.ReadLine();
                    var values = line.Split(';');
                    if (values.Length >= 7)
                    {
                        var importedObject = new ImportedObject()
                        {
                            Type = values[0].Trim(),
                            Name = values[1].Trim(),
                            Schema = values[2].Trim(),
                            ParentName = values[3].Trim(),
                            ParentType = values[4].Trim(),
                            DataType = values[5].Trim(),
                            IsNullable = values[6].Trim()
                        };
                        ((List<ImportedObject>)ImportedObjects).Add(importedObject);

                    }
                    
                    
                }

                // clear and correct imported data
                foreach (var importedObject in ImportedObjects)
                {
                    importedObject.Type = importedObject.Type.ToUpper();
                    importedObject.Name = importedObject.Name.Replace(" ", "").Replace(Environment.NewLine, "");
                    importedObject.Schema = importedObject.Schema.Replace(" ", "").Replace(Environment.NewLine, "");
                    importedObject.ParentName = importedObject.ParentName.Replace(" ", "").Replace(Environment.NewLine, "");
                    importedObject.ParentType = importedObject.ParentType.Replace(" ", "").Replace(Environment.NewLine, "");
                }

                // assign number of children
                foreach (var importedObject in ImportedObjects)
                {
                    foreach (var impObj in ImportedObjects)
                    {
                        if (string.Equals(impObj.ParentType, importedObject.Type, StringComparison.OrdinalIgnoreCase) &&
                            impObj.ParentName == importedObject.Name)
                        {
                            importedObject.NumberOfChildren++;
                        }
                    }
                }

                foreach (var database in ImportedObjects)
                {
                    if (database.Type == "DATABASE")
                    {
                        Console.WriteLine($"Database '{database.Name}' ({database.NumberOfChildren} tables)");

                        // print all database's tables
                        foreach (var table in ImportedObjects)
                        {
                            if (table.ParentType.ToUpper() == database.Type)
                            {
                                if (table.ParentName == database.Name)
                                {
                                    Console.WriteLine($"\tTable '{table.Schema}.{table.Name}' ({table.NumberOfChildren} columns)");

                                    // print all table's columns
                                    foreach (var column in ImportedObjects)
                                    {
                                        if (column.ParentType.ToUpper() == table.Type)
                                        {
                                            if (column.ParentName == table.Name)
                                            {
                                                Console.WriteLine($"\t\tColumn '{column.Name}' with {column.DataType} data type {(column.IsNullable == "1" ? "accepts nulls" : "with no nulls")}");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Console.ReadLine();
            }
        }

        class ImportedObject : ImportedObjectBaseClass
        {

            public string Schema;

            public string ParentName;
            public string ParentType
            {
                get; set;
            }

            public string DataType { get; set; }
            public string IsNullable { get; set; }

            public double NumberOfChildren;
        }

        class ImportedObjectBaseClass
        {
            public string Name { get; set; }
            public string Type { get; set; }
        }
    }
}
